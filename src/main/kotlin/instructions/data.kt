package org.cuttlefish.instructions

import org.cuttlefish.data.*

fun Instruction.pop(register: RegisterAddress): WriteBackOutput {
    useful
    return WriteBackOutput(Stack.pop(), register,listOf(register))
}

fun Instruction.push(register: RegisterValue) {
    useful
    Stack.push(register.value)
}

fun Instruction.mov(register1: RegisterValue, register2: RegisterAddress): WriteBackOutput {
    useful
    return WriteBackOutput(register1.value, register2,listOf(register1,register2))
}

fun Instruction.li(register: RegisterAddress, value: Short): WriteBackOutput {
    useful
    return WriteBackOutput(value, register,listOf(register,value))
}

fun Instruction.pr(register: RegisterValue) {
    useful
    println(register.value)
}


fun Instruction.load(register1: RegisterValue, address: RegisterAddress): WriteBackOutput {
    useful
    return WriteBackOutput(register1.value, address,listOf(register1, address))

}

fun Instruction.store(register1: RegisterValue, address: RegisterValue): MEMWruteBackOutput {
    useful
    return MEMWruteBackOutput(
        value = register1.value,
        location = MemoryAddress(address.value),
        arguments = listOf(register1, address)
    )
}