package org.cuttlefish.pipelining

import org.cuttlefish.data.RegisterAddress
import org.cuttlefish.data.RegisterValue
import org.cuttlefish.instructions.Instruction.mappings
import org.cuttlefish.instructions.InstructionType


/**
 * 3 Execute the operation or calculate address
 */
class EX(decoded: ID) {
    val name = decoded.name
    val fmt = decoded.fmt
    val kFunctionExe = mappings[name]!![0]
    val fmtStructure = decoded.fillInRegisters()

    init {
        when (fmt) {
            InstructionType.Register1 -> {

                if (!(name != "pr" && name != "push")) {
                    @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue) -> Unit).invoke(fmtStructure[1] as RegisterValue)
                } else {
                    @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterAddress) -> Unit).invoke(fmtStructure[1] as RegisterAddress)
                }
            }


            InstructionType.Register3 -> {
                @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue, RegisterValue, RegisterAddress) -> Unit).invoke(
                    fmtStructure[1] as RegisterValue,
                    fmtStructure[2] as RegisterValue,
                    fmtStructure[3] as RegisterAddress,
                )

            }

            InstructionType.Register2 -> {

                if (name == "st") {
                    @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue, RegisterValue) -> Unit).invoke(
                        fmtStructure[1] as RegisterValue,
                        fmtStructure[2] as RegisterValue,
                    )
                } else {
                    @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue, RegisterAddress) -> Unit).invoke(
                        fmtStructure[1] as RegisterValue,
                        fmtStructure[2] as RegisterAddress,
                    )
                }
            }

            InstructionType.Immediates -> {
                @Suppress("UNCHECKED_CAST") (kFunctionExe as (UShort) -> Unit).invoke(
                    (fmtStructure[1] as UShort)
                )
            }

            InstructionType.StandAlone -> {

            }

            InstructionType.RegisterImmediates -> {


                if (name == "li") { // RegisterADDRESS
                    @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterAddress, Short) -> Unit).invoke(
                        fmtStructure[1] as RegisterAddress, (fmtStructure[2] as Short)
                    )
                } else {
                    @Suppress("UNCHECKED_CAST") (kFunctionExe as (RegisterValue, Short) -> Unit).invoke(
                        fmtStructure[1] as RegisterValue, (fmtStructure[2] as Short)
                    )
                }
            }
        }
    }
}