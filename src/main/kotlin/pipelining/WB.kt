package org.cuttlefish.pipelining

import org.cuttlefish.DataFlow
import org.cuttlefish.util.Maybe



class WB(val p4memDataFlow: DataFlow) {

    suspend fun writeBack() = when (val wb = p4memDataFlow.writeBack) {
        is Maybe.Some -> {
            val (value, location) = wb.value
            location.value.write(value)
            p4memDataFlow
        }

        is Maybe.Not -> p4memDataFlow

    }
}