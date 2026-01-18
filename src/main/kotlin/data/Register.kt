package org.cuttlefish.data

import org.cuttlefish.instructions.InstructionType

enum class Register {
    R1, R2, R3, R4, PC, INSTR;

    private var data: Short = 0
    fun read(): Short = data
    fun write(data: Short) {
        this.data = data
    }

}