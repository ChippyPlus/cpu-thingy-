package org.cuttlefish.instructions

import org.cuttlefish.data.Register


fun Instruction.add(register1: Register, register2: Register, register3: Register) = run {
    useful
    register3.write((register1.read() + register2.read()).toByte())
}

fun Instruction.sub(register1: Register, register2: Register, register3: Register) = run {
    useful
    register3.write((register1.read() - register2.read()).toByte())
}

fun Instruction.mul(register1: Register, register2: Register, register3: Register) = run {
    useful
    register3.write((register1.read() * register2.read()).toByte())
}

fun Instruction.div(register1: Register, register2: Register, register3: Register) = run {
    useful
    register3.write((register1.read() / register2.read()).toByte())
}