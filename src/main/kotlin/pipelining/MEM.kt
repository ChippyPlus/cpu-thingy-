package org.cuttlefish.pipelining

import org.cuttlefish.data.Memory
import org.cuttlefish.data.PipeBuffer
import org.cuttlefish.data.Register
import org.cuttlefish.data.WriteBackOutput


/**
 * 4 Access an operand in data memory
 */
class MEM() {
    fun memoryWriteBack() {
        println("memory writing backing")
        when (val ex = PipeBuffer.pex!!) {
            EXResult.Empty -> {}
            is EXResult.Register -> {
                if ("ld" == ID((Register.INSTR.readPrivilege())).name) {
                    val value = ex.value.value
                    val memEX = Memory.read(value)
                    PipeBuffer.pwb = WriteBackOutput(memEX, ex.value.location)
                }
            }

            is EXResult.Memory -> {
                if ("st" == ID((Register.INSTR.readPrivilege())).name) {
                    val location = ex.value.location.value
                    val value = ex.value.value
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