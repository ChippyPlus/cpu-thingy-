package org.cuttlefish.instructions

import org.cuttlefish.data.Register


fun Instruction.j(line: UInt) {
    useful
    Register.PC.writeToPc(line)
}

fun Instruction.jnz(line: UInt, register: Register) {
    useful
    if (register.read() != 0.toByte()) {
        Register.PC.writeToPc(line)
    }
}

fun Instruction.jz(line: UInt, register: Register) {
    useful
    if (register.read() == 0.toByte()) {
        Register.PC.writeToPc(line)
    }
}
