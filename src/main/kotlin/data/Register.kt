package org.cuttlefish.data

enum class Register {
    R1, R2, R3, R4, PC, INSTR;

    private var data: Short = 0
    fun read(): Short = data
    fun write(data: Short) {
        this.data = data
    }

    fun inc() {
        data = (data + 1.toShort()).toShort()
    }

}