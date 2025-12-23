package org.cuttlefish.instructions

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
