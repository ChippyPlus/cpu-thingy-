package org.cuttlefish.instructions

import org.cuttlefish.data.Register


fun Instruction.shl(register1: Register, register2: Register, register3: Register) {
    useful
    val r1 = register1.read()
    val r2 = register2.read()
    val result = (r1.toInt() shl r2.toInt()).toShort()
    register3.write(result)
}

fun Instruction.shr(register1: Register, register2: Register, register3: Register) {
    useful
    val r1 = register1.read()
    val r2 = register2.read()
    val result = (r1.toInt() shr r2.toInt()).toShort()
    register3.write(result)
}

fun Instruction.and(register1: Register, register2: Register, register3: Register) {
    useful
    val r1 = register1.read()
    val r2 = register2.read()
    val result = (r1.toInt() and r2.toInt()).toShort()
    register3.write(result)
}

fun Instruction.or(register1: Register, register2: Register, register3: Register) {
    useful
    val r1 = register1.read()
    val r2 = register2.read()
    val result = (r1.toInt() or r2.toInt()).toShort()
    register3.write(result)
}

fun Instruction.not(register1: Register, register2: Register) {
    useful
    val r1 = register1.read()
    val result = (r1.toInt().inv()).toShort()
    register2.write(result)
}

fun Instruction.xor(register1: Register, register2: Register, register3: Register) {
    useful
    val r1 = register1.read()
    val r2 = register2.read()
    val result = (r1.toInt() xor r2.toInt()).toShort()
    register3.write(result)
}
