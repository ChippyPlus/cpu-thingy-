package org.cuttlefish.pipelining

import org.cuttlefish.data.MEMWruteBackOutput
import org.cuttlefish.data.RegisterAddress
import org.cuttlefish.data.RegisterValue
import org.cuttlefish.data.WriteBackOutput
import org.cuttlefish.instructions.Instruction.mappings
import org.cuttlefish.instructions.InstructionType


sealed interface ExecutionResult {
    data class Register(val value: WriteBackOutput) : ExecutionResult
    data class Memory(val value: MEMWruteBackOutput) : ExecutionResult
    object Empty : ExecutionResult
}

/**
 * 3 Execute the operation or calculate address
 */
class EX(decoded: ID) {
    val name = decoded.name
    val fmt = decoded.fmt
    val kFunctionExe = mappings[name]!![0]
    val fmtStructure = decoded.fillInRegisters()
    val wb: ExecutionResult = when (fmt) {
        InstructionType.Register1 -> {

            if (name == "pop") {
                val res =
                    @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue) -> WriteBackOutput).invoke(fmtStructure[1] as RegisterValue)
                ExecutionResult.Register(res)
            } else {
                @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterAddress) -> Unit).invoke(fmtStructure[1] as RegisterAddress)
                ExecutionResult.Empty
            }
        }


        InstructionType.Register3 -> {
            val res =
                @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue, RegisterValue, RegisterAddress) -> WriteBackOutput).invoke(
                    fmtStructure[1] as RegisterValue,
                    fmtStructure[2] as RegisterValue,
                    fmtStructure[3] as RegisterAddress,
                )
            ExecutionResult.Register(res)
        }

        InstructionType.Register2 -> {

            if (name == "st") {
                val res =
                    @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue, RegisterValue) -> MEMWruteBackOutput).invoke(
                        fmtStructure[1] as RegisterValue,
                        fmtStructure[2] as RegisterValue,
                    )
                ExecutionResult.Memory(res)
            } else {
                val res =
                    @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue, RegisterAddress) -> WriteBackOutput).invoke(
                        fmtStructure[1] as RegisterValue,
                        fmtStructure[2] as RegisterAddress,
                    )
                ExecutionResult.Register(res)
            }
        }

        InstructionType.Immediates -> {
            val res = @Suppress("UNCHECKED_CAST") (kFunctionExe as (UShort) -> Unit).invoke(
                (fmtStructure[1] as UShort)
            )
            ExecutionResult.Empty
        }

        InstructionType.StandAlone -> {
            ExecutionResult.Empty
        }

        InstructionType.RegisterImmediates -> {


            if (name == "li") { // RegisterADDRESS
                val res =
                    @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterAddress, Short) -> WriteBackOutput).invoke(
                        fmtStructure[1] as RegisterAddress, (fmtStructure[2] as Short)
                    )
                ExecutionResult.Register(res)
            } else {
                @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue, Short) -> Unit).invoke(
                    fmtStructure[1] as RegisterValue, (fmtStructure[2] as Short)
                )
                ExecutionResult.Empty
            }
        }
    }

}