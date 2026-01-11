package org.cuttlefish.pipelining

import org.cuttlefish.data.Memory
import org.cuttlefish.data.Register
import org.cuttlefish.data.WriteBackOutput


/**
 * 4 Access an operand in data memory
 */
class MEM(ex: EX) {

    val streamToWB =
        if (ex.result is EXResult.Register && "ld" == ID((Register.INSTR.readPrivilege())).name) {
            val value = ex.result.value.value
            val memEX = Memory.read(value)
            WriteBackOutput(memEX, ex.result.value.location)
        } else {
            null
        }


    init {
        when (ex.result) {
            EXResult.Empty -> {}
            is EXResult.Register -> {
                if ("ld" == ID((Register.INSTR.readPrivilege())).name) {
                    val value = ex.result.value.value
                    val memEX = Memory.read(value)
                    val wb = WriteBackOutput(memEX, ex.result.value.location)
                }
            }

            is EXResult.Memory -> {
                if ("st" == ID((Register.INSTR.readPrivilege())).name) {
                    val location = ex.result.value.location.value
                    val value = ex.result.value.value
                    Memory.write(location, value)
                } else {
                    throw IllegalArgumentException(
                        "Huh??? it was ${
                            opcodeMapFullMeta[Register.INSTR.readPrivilege().toInt() shl 16 - 5]!![0]
                        }????"
                    )
                }
            }
        }
    }
}