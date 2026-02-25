package org.cuttlefish.pipelining

import org.cuttlefish.AluResult
import org.cuttlefish.DataFlow
import org.cuttlefish.MemWriteOutput
import org.cuttlefish.WriteBackOutput
import org.cuttlefish.data.MEMWruteBackOutput_old
import org.cuttlefish.data.RegisterAddress
import org.cuttlefish.data.RegisterValue
import org.cuttlefish.data.WriteBackOutput_old
import org.cuttlefish.instructions.Instruction.mappings
import org.cuttlefish.instructions.InstructionType
import org.cuttlefish.util.Maybe

@Deprecated("In favour of DataFlow")
sealed interface EXResult {
    data class Register(val returns: WriteBackOutput_old) : EXResult
    data class Memory(val returns: MEMWruteBackOutput_old) : EXResult
    object Empty : EXResult
}

/**
 * 3 Execute the operation or calculate address
 */
class EX(val p2idDataFlow: DataFlow) {
    var aluResult: AluResult? = null

    private suspend fun innerExecute() = when (p2idDataFlow.decode) {
        is Maybe.Some -> {
            val decoded = p2idDataFlow.decode.value
            val fmt = decoded.format
            val kFunctionExe = mappings[decoded.name]!![0]
            val fmtStructure = decoded.registerStructure
            aluResult = when (fmt) {
                InstructionType.Register1 -> {

                    if (decoded.name == "pop") {
                        val res =
                            @Suppress("UNCHECKED_CAST") (kFunctionExe as suspend (RegisterAddress) -> WriteBackOutput_old).invoke(
                                fmtStructure[1] as RegisterAddress
                            )
                        AluResult.Register(WriteBackOutput(value = res.value, location = res.location))

                    } else {
                        @Suppress("UNCHECKED_CAST") (kFunctionExe as suspend (RegisterValue) -> Unit).invoke(
                            fmtStructure[1] as RegisterValue
                        )
                        AluResult.Empty
                    }
                }


                InstructionType.Register3 -> {
                    val res =
                        @Suppress("UNCHECKED_CAST") (kFunctionExe as suspend (RegisterValue, RegisterValue, RegisterAddress) -> WriteBackOutput_old).invoke(
                            fmtStructure[1] as RegisterValue,
                            fmtStructure[2] as RegisterValue,
                            fmtStructure[3] as RegisterAddress,
                        )

                    AluResult.Register(WriteBackOutput(value = res.value, location = res.location))
                }

                InstructionType.Register2 -> {

                    if (decoded.name == "st") {
                        val res: MEMWruteBackOutput_old =
                            @Suppress("UNCHECKED_CAST") (kFunctionExe as suspend (RegisterValue, RegisterValue) -> MEMWruteBackOutput_old).invoke(
                                fmtStructure[1] as RegisterValue,
                                fmtStructure[2] as RegisterValue,
                            )
                        AluResult.Memory(
                            returns = MemWriteOutput(
                                value = res.value,
                                location = res.location,
                                opName = res.opName,
                                optionalRegisterLocation = null
                            )
                        )
                    } else if (decoded.name == "ld") {
                        val res: MEMWruteBackOutput_old =
                            @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue, RegisterAddress) -> MEMWruteBackOutput_old).invoke(
                                fmtStructure[1] as RegisterValue,
                                fmtStructure[2] as RegisterAddress,
                            )
                        AluResult.Memory(
                            returns = MemWriteOutput(
                                value = res.value,
                                location = res.location,
                                opName = res.opName,
                                optionalRegisterLocation = null
                            )
                        )
                    } else {
                        val res: WriteBackOutput_old =
                            @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue, RegisterAddress) -> WriteBackOutput_old).invoke(
                                fmtStructure[1] as RegisterValue,
                                fmtStructure[2] as RegisterAddress,
                            )
                        AluResult.Register(WriteBackOutput(value = res.value, location = res.location))

                    }
                }

                InstructionType.Immediates -> {
                    @Suppress("UNCHECKED_CAST") (kFunctionExe as (UShort) -> Unit).invoke(
                        (fmtStructure[1] as UShort)
                    )
                    AluResult.Empty
                }

                InstructionType.StandAlone -> {
                    AluResult.Empty
                }

                InstructionType.RegisterImmediates -> {


                    if (decoded.name == "li") { // RegisterADDRESS
                        val res =
                            @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterAddress, Short) -> WriteBackOutput_old).invoke(
                                fmtStructure[1] as RegisterAddress, (fmtStructure[2] as Short)
                            )
                        AluResult.Register(WriteBackOutput(value = res.value, location = res.location))

                    } else {
                        @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue, Short) -> Unit).invoke(
                            fmtStructure[1] as RegisterValue, (fmtStructure[2] as Short)
                        )
                        AluResult.Empty
                    }
                }
            }
        }

        is Maybe.Not -> throw IllegalStateException()
    }

    suspend fun execute(): DataFlow {
        innerExecute()
        return when (val r = aluResult!!) {
            is AluResult.Register -> {
                p2idDataFlow.copy(writeBack = Maybe.Some(r.returns))
            }

            is AluResult.Memory -> {
                p2idDataFlow.copy(memWrite = Maybe.Some(r.returns))
            }

            is AluResult.Empty -> p2idDataFlow
        }
    }


}