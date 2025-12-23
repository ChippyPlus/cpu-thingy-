package org.cuttlefish.data


object Memory {
    const val SIZE = 256
    private val memory = ShortArray(SIZE) { -1 }
    fun write(address: Short, value: Short) {
        if (validAddress(address)) {
            memory[address.toInt()] = value
        }
    }

    fun read(address: Short): Short {
        if (!validAddress(address)) {
            return -1 // Would've thrown an exception!!
        }
        return memory[address.toInt()]
    }

    private fun validAddress(address: Short): Boolean {
        if (address !in 0..<SIZE) {
            return false
        }
        return true
    }

}