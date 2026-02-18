package org.cuttlefish.pipelining

import org.cuttlefish.data.Memory
import org.cuttlefish.data.PipeBuffer
import org.cuttlefish.data.RegisterAddress
import org.cuttlefish.data.WriteBackOutput


/**
 * 4 Access an operand in data memory
 */
class MEM {
    fun memoryWriteBack() {
//        println("memory writing backing")

        if (PipeBuffer.pmem != null) {
            when (ID().name) {
                "st" -> {
                    val location = PipeBuffer.pmem!!.location.value
                    val value = PipeBuffer.pmem!!.value
                    Memory.write(location, value)
                }

                "ld" -> {
                    val value = PipeBuffer.pmem!!.value
                    val memEX = Memory.read(value)
                    PipeBuffer.pwb = WriteBackOutput(
                        memEX,
                        PipeBuffer.pmem!!.arguments[1] as RegisterAddress,
                        PipeBuffer.pmem!!.arguments
                    )

                }

                else -> {
                    throw IllegalArgumentException(
                        "Huh??? it was ${
                            ID().name
                            //                        opcodeMapFullMeta[Register.INSTR.readPrivilege().toInt() shl 16 - 5]!![0]
                        }????"
                    )
                }
            }
        }
    }
}

//
//        when (val ex = PipeBuffer.pex!!) {
//            EXResult.Empty -> {}
//            is EXResult.Register -> {
//                if ("ld" == ID().name) {
//                    val value = ex.value.value
//                    val memEX = Memory.read(value)
//                    PipeBuffer.pwb = WriteBackOutput(memEX, ex.value.location)
//                }
//            }
//
//            is EXResult.Memory -> {
//                if ("st" == ID().name) {
//                    val location = ex.value.location.value
//                    val value = ex.value.value
//                    Memory.write(location, value)
//                } else {
//                    throw IllegalArgumentException(
//                        "Huh??? it was ${
//                            opcodeMapFullMeta[Register.INSTR.readPrivilege().toInt() shl 16 - 5]!![0]
//                        }????"
//                    )
//                }
//            }
//        }
//
//    }

