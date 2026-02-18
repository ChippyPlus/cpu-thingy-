package org.cuttlefish.instructions

import org.cuttlefish.data.RegisterAddress
import org.cuttlefish.data.WriteBackOutput
import org.cuttlefish.data.RegisterValue


fun Instruction.add(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    return WriteBackOutput((register1.value + register2.value).toShort(), register3, listOf(register1, register2, register3))
}

fun Instruction.sub(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    return WriteBackOutput((register1.value - register2.value).toShort(), register3, listOf(register1, register2, register3))
}

fun Instruction.mul(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    return WriteBackOutput((register1.value * register2.value).toShort(), register3, listOf(register1, register2, register3))
}

fun Instruction.div(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    return WriteBackOutput((register1.value / register2.value).toShort(), register3, listOf(register1, register2, register3))
}