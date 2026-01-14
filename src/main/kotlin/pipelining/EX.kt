package org.cuttlefish.pipelining

import org.cuttlefish.data.*
import org.cuttlefish.instructions.Instruction.mappings
import org.cuttlefish.instructions.InstructionType


sealed interface EXResult {
    data class Register(val value: WriteBackOutput) : EXResult
    data class Memory(val value: MEMWruteBackOutput) : EXResult
    object Empty : EXResult
}

/**
 * 3 Execute the operation or calculate address
 */
class EX {
    fun execute() {
        println("executing")
        val decoded = PipeBuffer.pid!!
        val name = decoded.name
        val fmt = decoded.format
        val kFunctionExe = mappings[name]!![0]
        val fmtStructure = decoded.registerStructure
        val result: EXResult = when (fmt) {
            InstructionType.Register1 -> {

                if (name == "pop") {
                    val res =
                        @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterAddress) -> WriteBackOutput).invoke(
                            fmtStructure[1] as RegisterAddress
                        )
                    EXResult.Register(res)
                } else {
                    @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue) -> Unit).invoke(fmtStructure[1] as RegisterValue)
                    EXResult.Empty
                }
            }


            InstructionType.Register3 -> {
                val res =
                    @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue, RegisterValue, RegisterAddress) -> WriteBackOutput).invoke(
                        fmtStructure[1] as RegisterValue,
                        fmtStructure[2] as RegisterValue,
                        fmtStructure[3] as RegisterAddress,
                    )
                EXResult.Register(res)
            }

            InstructionType.Register2 -> {

                if (name == "st") {
                    val res =
                        @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue, RegisterValue) -> MEMWruteBackOutput).invoke(
                            fmtStructure[1] as RegisterValue,
                            fmtStructure[2] as RegisterValue,
                        )
                    EXResult.Memory(res)
                }
//                else if (name == "ld") {
//                    val res =
//                        @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue, RegisterValue) -> MEMWruteBackOutput).invoke(
//                            fmtStructure[1] as RegisterValue,
//                            fmtStructure[2] as RegisterValue,
//                        )
//                    EXResult.Memory(res)
//                }
                else {
                    val res =
                        @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue, RegisterAddress) -> WriteBackOutput).invoke(
                            fmtStructure[1] as RegisterValue,
                            fmtStructure[2] as RegisterAddress,
                        )
                    EXResult.Register(res)
                }
            }

            InstructionType.Immediates -> {
                @Suppress("UNCHECKED_CAST") (kFunctionExe as (UShort) -> Unit).invoke(
                    (fmtStructure[1] as UShort)
                )
                EXResult.Empty
            }

            InstructionType.StandAlone -> {
                EXResult.Empty
            }

            InstructionType.RegisterImmediates -> {


                if (name == "li") { // RegisterADDRESS
                    val res =
                        @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterAddress, Short) -> WriteBackOutput).invoke(
                            fmtStructure[1] as RegisterAddress, (fmtStructure[2] as Short)
                        )
                    EXResult.Register(res)
                } else {
                    @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue, Short) -> Unit).invoke(
                        fmtStructure[1] as RegisterValue, (fmtStructure[2] as Short)
                    )
                    EXResult.Empty
                }
            }
        }

        when (result) {
            is EXResult.Register -> {
                PipeBuffer.pwb = result.value
            }

            is EXResult.Memory -> {
                PipeBuffer.pmem = result.value
            }

            is EXResult.Empty -> {}
        }

    }


}