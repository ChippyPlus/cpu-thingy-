package org.cuttlefish.instructions

import org.cuttlefish.data.*

fun Instruction.pop(register: RegisterAddress): WriteBackOutput_old {
    useful
    return WriteBackOutput_old(Stack.pop(), register)
}

fun Instruction.push(register: RegisterValue) {
    useful
    Stack.push(register.value)
}

fun Instruction.mov(register1: RegisterValue, register2: RegisterAddress): WriteBackOutput_old {
    useful
    return WriteBackOutput_old(register1.value, register2)
}

fun Instruction.li(register: RegisterAddress, value: Short): WriteBackOutput_old {
    useful
    return WriteBackOutput_old(value, register)
}

fun Instruction.pr(register: RegisterValue) {
    useful
    println(register.value)
}


fun Instruction.load(register1: RegisterValue, address: RegisterAddress): MEMWruteBackOutput_old {
    useful

    return MEMWruteBackOutput_old(
        value = 0,
        location = MemoryAddress(register1.value),
        opName = 'l',
        optionalRegisterLocation = address
    )

}

fun Instruction.store(register1: RegisterValue, address: RegisterValue): MEMWruteBackOutput_old {
    useful
    return MEMWruteBackOutput_old(
        value = register1.value,
        location = MemoryAddress(address.value),
        opName = 's',
        null
    )
}