package org.cuttlefish.instructions

import org.cuttlefish.data.Register


fun Instruction.eq(register1: Register, register2: Register, register3: Register) {
    useful
    register3.write(if (register1.read() == register2.read()) 1 else 0)
}

fun Instruction.neq(register1: Register, register2: Register, register3: Register) {
    useful
    register3.write(if (register1.read() != register2.read()) 1 else 0)
}

fun Instruction.lt(register1: Register, register2: Register, register3: Register) {
    useful
    register3.write(if (register1.read() < register2.read()) 1 else 0)
}

fun Instruction.gt(register1: Register, register2: Register, register3: Register) {
    useful
    register3.write(if (register1.read() > register2.read()) 1 else 0)
}

fun Instruction.lte(register1: Register, register2: Register, register3: Register) {
    useful
    register3.write(if (register1.read() <= register2.read()) 1 else 0)
}

fun Instruction.gte(register1: Register, register2: Register, register3: Register) {
    useful
    register3.write(if (register1.read() >= register2.read()) 1 else 0)
}
