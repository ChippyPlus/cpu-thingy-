package org.cuttlefish.instructions

import org.cuttlefish.data.Register


fun Instruction.j(line: UShort) {
    useful
    Register.PC.writeToPc(line)
}

fun Instruction.jnz(line: UShort, register: Register) {
    useful
    if (register.read() != 0.toShort()) {
        Register.PC.writeToPc(line)
    }
}

fun Instruction.jz(line: UShort, register: Register) {
    useful
    if (register.read() == 0.toShort()) {
        Register.PC.writeToPc(line)
    }
}
