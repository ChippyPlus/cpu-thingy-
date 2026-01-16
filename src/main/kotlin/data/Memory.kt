package org.cuttlefish.data

import kotlinx.coroutines.delay


object Memory {
    const val SIZE = 256
    private val memory = ShortArray(SIZE) { -1 }
    suspend fun write(address: Short, value: Short) {
        delay(Clock.SPEED_MEMORY_WRITE)
        if (validAddress(address)) {
            memory[address.toInt()] = value
        }
    }

    suspend fun read(address: Short): Short {
        delay(Clock.SPEED_MEMORY_READ)

        if (!validAddress(address)) {
            return -1 // Would've thrown an exception!! What a waste! You should know better 🙄
        }
        return memory[address.toInt()]
    }

    private fun validAddress(address: Short): Boolean {
        return address in 0..<SIZE
    }

}