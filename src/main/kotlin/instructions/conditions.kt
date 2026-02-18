package org.cuttlefish.instructions

import org.cuttlefish.data.RegisterAddress
import org.cuttlefish.data.WriteBackOutput
import org.cuttlefish.data.RegisterValue


fun Instruction.eq(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    return WriteBackOutput(if (register1.value == register2.value) 1.toShort() else 0.toShort(), register3, listOf(register1, register2, register3))
}

fun Instruction.neq(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    return WriteBackOutput(if (register1.value != register2.value) 1.toShort() else 0.toShort(), register3, listOf(register1, register2, register3))
}

fun Instruction.lt(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    return WriteBackOutput(if (register1.value < register2.value) 1.toShort() else 0.toShort(), register3, listOf(register1, register2, register3))
}

fun Instruction.gt(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    return WriteBackOutput(if (register1.value > register2.value) 1.toShort() else 0.toShort(), register3, listOf(register1, register2, register3))
}

fun Instruction.lte(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    return WriteBackOutput(if (register1.value <= register2.value) 1.toShort() else 0.toShort(), register3, listOf(register1, register2, register3))
}

fun Instruction.gte(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    return WriteBackOutput(if (register1.value >= register2.value) 1.toShort() else 0.toShort(), register3, listOf(register1, register2, register3))
}