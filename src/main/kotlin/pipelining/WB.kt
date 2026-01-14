package org.cuttlefish.pipelining

import org.cuttlefish.data.PipeBuffer


/**
 * 5 Write the result into a register
 */
class WB {

    fun writeBack() {
        println("writing backing")
        if (PipeBuffer.pwb != null) {
            val value = PipeBuffer.pwb!!.value
            val location = PipeBuffer.pwb!!.location.value
            location.write(value)
        }
    }
}