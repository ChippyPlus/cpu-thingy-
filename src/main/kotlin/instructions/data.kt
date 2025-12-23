package org.cuttlefish.instructions

import org.cuttlefish.data.Memory
import org.cuttlefish.data.Register
import org.cuttlefish.data.Stack

fun Instruction.pop(register: Register) {
    useful
    register.write(Stack.pop())
}

fun Instruction.push(register: Register) {
    useful
    Stack.push(register.read())
}

fun Instruction.mov(register1: Register, register2: Register) {
    useful
    register2.write(register1.read())
}

fun Instruction.swp(register1: Register, register2: Register) {
    useful
    val temp = register1.read()
    register1.write(register2.read())
    register2.write(temp)
}

fun Instruction.li(register: Register, value: Short) {
    useful
    register.write(value)
}

fun Instruction.pr(register: Register) {
    useful
    println(register.read())
}


fun Instruction.load(register1: Register, address: Register) {
    useful
    val memoryOut = Memory.read(address.read())
    register1.write(memoryOut)
}

fun Instruction.store(register1: Register, address: Register) {
    useful
    val memoryIn = register1.read()
    val address = address.read()
    Memory.write(address, memoryIn)
}