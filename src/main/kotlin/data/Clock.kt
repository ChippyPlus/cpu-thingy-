package org.cuttlefish.data

import kotlinx.coroutines.delay

object Clock {
    const val MAX_CYCLES = 50
    const val SPEED_ALU = 10L
    const val SPEED_REGISTER_READ = 1L
    const val SPEED_REGISTER_WRITE = 5L
    const val SPEED_MEMORY_READ = 10L
    const val SPEED_MEMORY_WRITE = 50L

    private var currentCycle = 1

    suspend fun tick(block: suspend (Int) -> Unit): Boolean {
        if (currentCycle > MAX_CYCLES) return false

        block(currentCycle)
        delay(1)
        currentCycle++
        return true
    }
}