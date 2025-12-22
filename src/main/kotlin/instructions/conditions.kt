package org.cuttlefish.instructions

import org.cuttlefish.data.Register


fun Instruction.eq(register1: Register, register2: Register, register3: Register) {
    useful
    register3.write(if (register1.read() == register2.read()) 1.toShort() else 0.toShort())
}

fun Instruction.neq(register1: Register, register2: Register, register3: Register) {
    useful
    register3.write(if (register1.read() != register2.read()) 1.toShort() else 0.toShort())
}

fun Instruction.lt(register1: Register, register2: Register, register3: Register) {
    useful
    register3.write(if (register1.read() < register2.read()) 1.toShort() else 0.toShort())
}

fun Instruction.gt(register1: Register, register2: Register, register3: Register) {
    useful
    register3.write(if (register1.read() > register2.read()) 1.toShort() else 0.toShort())
}

fun Instruction.lte(register1: Register, register2: Register, register3: Register) {
    useful
    register3.write(if (register1.read() <= register2.read()) 1.toShort() else 0.toShort())
}

fun Instruction.gte(register1: Register, register2: Register, register3: Register) {
    useful
    register3.write(if (register1.read() >= register2.read()) 1.toShort() else 0.toShort())
}
