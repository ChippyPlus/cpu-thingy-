package org.cuttlefish.data

enum class Register {
    R1, R2, R3, R4, PC, INSTR;

    private var data: Short = 0
    private var dataSpecial: UShort = 0u
    fun read(): Short = data
    fun write(data: Short) {
        if (this == PC) throw IllegalStateException("Writing to PC is not allowed!!!!")
        this.data = data
    }

    fun readPrivilege(): UShort {
        if (this == PC || this == INSTR) return dataSpecial
        else throw IllegalStateException("This is only for the PRIVILEGED REGISTERS!!!!!")
    }

    fun writePrivilege(data: UShort) {
        if (this == PC || this == INSTR) dataSpecial = data
        else throw IllegalStateException("This is only for the PRIVILEGED REGISTERS!!!!!")
    }

}

@JvmInline
value class RegisterValue(val value: Short)

@JvmInline
value class RegisterAddress(val value: Register)

@JvmInline
value class MemoryAddress(val value: Short)

data class WriteBackOutput(val value: Short, val location: RegisterAddress)
data class MEMWruteBackOutput( val value: Short,  val location: MemoryAddress)