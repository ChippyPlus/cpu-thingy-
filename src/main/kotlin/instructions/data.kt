package org.cuttlefish.instructions

import org.cuttlefish.data.*

fun Instruction.pop(register: RegisterAddress): WriteBackOutput {
    useful
    return WriteBackOutput(Stack.pop(), register)
}

fun Instruction.push(register: RegisterValue) {
    useful
    Stack.push(register.value)
}

fun Instruction.mov(register1: RegisterValue, register2: RegisterAddress): WriteBackOutput {
    useful
    return WriteBackOutput(register1.value, register2)
}

fun Instruction.li(register: RegisterAddress, value: Short): WriteBackOutput {
    useful
    return WriteBackOutput(value, register)
}

fun Instruction.pr(register: RegisterValue) {
    useful
    println(register.value)
}


fun Instruction.load(register1: RegisterValue, address: RegisterAddress): WriteBackOutput {
    useful
//    return null
//    val memoryOut = Memory.read(address.read())
//    register1.write(memoryOut)
    return WriteBackOutput(register1.value, address)

}

fun Instruction.store(register1: RegisterValue, address: RegisterValue): MEMWruteBackOutput {
    useful
//    val memoryIn = register1.read()
//    val address = address.read()
//    Memory.write(address, memoryIn)
    return MEMWruteBackOutput(register1.value, MemoryAddress(address.value))
}