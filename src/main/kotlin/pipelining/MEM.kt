package org.cuttlefish.pipelining

import org.cuttlefish.AluResult
import org.cuttlefish.DataFlow
import org.cuttlefish.WriteBackOutput
import org.cuttlefish.data.Memory
import org.cuttlefish.data.PipeBuffer
import org.cuttlefish.data.WriteBackOutput_old
import org.cuttlefish.util.Maybe


/**
 * 4 Access an operand in data memory
 */
class MEM(val p3exDataFlow: DataFlow) {

    suspend fun memoryWriteBack(): DataFlow = when (val x = PipeBuffer.p3ex.aluResult) {
        is Maybe.Some<*> if (x.value is AluResult.Memory) -> {


            val input = x.value.returns
            //  NO FUCKING TIME TRAVEL
            val newFlow: DataFlow = when (input.opName) {
                's' -> {
                    val location = input.location.value
                    val value = input.value
                    Memory.write(location, value)
                    p3exDataFlow
                }

                'l' -> {
                    val addressToRead = input.location.value
                    val memValue = Memory.read(addressToRead)

                    PipeBuffer.pwb_deprecated = WriteBackOutput_old(
                        value = memValue, location = input.optionalRegisterLocation!!
                    )

                    val newFlow = p3exDataFlow.copy(
                        writeBack = Maybe.Some(
                            value = WriteBackOutput(
                                value = memValue, location = input.optionalRegisterLocation
                            )
                        )
                    )
                    newFlow

                }

                else -> {
                    throw IllegalArgumentException("MEM stage received unknown op: ${input.opName}")
                }
            }
            newFlow

//            PipeBuffer.pmem_deprecated = null
        }

        is Maybe.Not -> {
            // Skipped :C
            p3exDataFlow
        }

        else -> {
            throw IllegalArgumentException()
        }
    }

}

