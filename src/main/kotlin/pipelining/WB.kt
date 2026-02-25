package org.cuttlefish.pipelining

import org.cuttlefish.data.PipeBuffer


/**
 * 5 Write the result into a register
 */
class WB {

    suspend fun writeBack() {
//        println("writing backing")
        if (PipeBuffer.pwb_deprecated != null) {
            val value = PipeBuffer.pwb_deprecated!!.value
            val location = PipeBuffer.pwb_deprecated!!.location.value
            location.write(value)
        }
    }
}