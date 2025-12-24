package org.cuttlefish.pipelining

import org.cuttlefish.data.Memory
import org.cuttlefish.data.Register


/**
 * 4 Access an operand in data memory
 */
class MEM(ex: EX) {
    init {
        when (ex.result) {
            EXResult.Empty -> {}
            is EXResult.Register -> {}
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

    val streamToWB = null
}