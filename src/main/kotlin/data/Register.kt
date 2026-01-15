package org.cuttlefish.data

import kotlinx.coroutines.delay

enum class Register {
    R1, R2, R3, R4, PC, INSTR;

    private var data: Short = 0
    private var dataSpecial: UShort = 0u
    suspend fun read(): Short {
        if (this == PC || this == INSTR) throw IllegalStateException("Reading from Privileged registers is not allowed!!!!")
        delay(Clock.SPEED_REGISTER_READ)
        return data
    }

    suspend fun write(data: Short) {
        delay(Clock.SPEED_REGISTER_WRITE)
        if (this == PC || this == INSTR) throw IllegalStateException("Writing to Privileged registers is not allowed!!!!")
        this.data = data
    }

    suspend fun readPrivilege(): UShort {
        delay(Clock.SPEED_REGISTER_READ)
        if (this == PC || this == INSTR) return dataSpecial
        else throw IllegalStateException("This is only for the PRIVILEGED REGISTERS!!!!!")
    }

    suspend fun writePrivilege(data: UShort) {
        delay(Clock.SPEED_REGISTER_WRITE)
        if (this == PC || this == INSTR) dataSpecial = data
        else throw IllegalStateException("This is only for the PRIVILEGED REGISTERS!!!!!")
    }
}