package org.cuttlefish.data

enum class Register {
    R0, R1, R2, R3, R4, R5, R6, R7;

    private var data: Byte = 0
    fun read(): Byte = data
    fun write(data: Byte) {
        this.data = data
    }
}
