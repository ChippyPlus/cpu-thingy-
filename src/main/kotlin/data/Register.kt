package org.cuttlefish.data

enum class Register {
    R0,
    R1,
    R2,
    R3,
    R4,
    R5,
    R6,
    R7,
    PC

    ;

    private var data: Byte = 0
    private var dataIfPc = 0u
    fun read(): Byte = data
    fun write(data: Byte) {
        if (this == PC) throw IllegalStateException("Writing to PC is not allowed!!!!")
        this.data = data
    }

    fun readToPc(): UInt = dataIfPc
    fun writeToPc(data: UInt) {
        if (this == PC) this.dataIfPc = data
        else throw IllegalStateException("This is only for the PC!!!!!")
    }

}
