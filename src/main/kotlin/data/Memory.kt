package org.cuttlefish.data

import kotlinx.coroutines.delay


object Memory {
    const val SIZE = 256
    val memory = ShortArray(SIZE) { -1 }
    suspend fun write(address: Short, value: Short) {
        delay(Clock.SPEED_MEMORY_WRITE)
        if (validAddress(address)) {
            memory[address.toInt()] = value
        }
    }

    suspend fun read(address: Short): Short {
        delay(Clock.SPEED_MEMORY_READ)

        if (!validAddress(address)) {
            throw IllegalStateException("Address $address is out of bounds (0..${SIZE - 1})")
        }

        return memory[address.toInt()]
    }

    private fun validAddress(address: Short): Boolean {
        return address in 0..<SIZE
    }

}