package org.cuttlefish.pipelining


/**
 * 5 Write the result into a register
 */
class WB(ex: EX) {
    val result = ex.result

    init {
        when (result) {
            EXResult.Empty -> {}
            is EXResult.Memory -> {}
            is EXResult.Register -> {
                val value = this.result.value.value
                val location = this.result.value.location.value
                location.write(value)
            }
        }
    }
}