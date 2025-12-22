package org.cuttlefish.data

enum class Register {
    R1, R2, R3, R4, PC;

    private var data: Short = 0
    private var dataIfPc: UShort = 0u
    fun read(): Short = data
    fun write(data: Short) {
        if (this == PC) throw IllegalStateException("Writing to PC is not allowed!!!!")
        this.data = data
    }

    fun readToPc(): UShort = dataIfPc
    fun writeToPc(data: UShort) {
        if (this == PC) this.dataIfPc = data
        else throw IllegalStateException("This is only for the PC!!!!!")
    }

}
