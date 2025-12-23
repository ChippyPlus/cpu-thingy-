package org.cuttlefish.instructions

import org.cuttlefish.data.RegisterAddress
import org.cuttlefish.data.WriteBackOutput
import org.cuttlefish.data.RegisterValue


fun Instruction.shl(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    val r1 = register1.value
    val r2 = register2.value
    return WriteBackOutput((r1.toInt() shl r2.toInt()).toShort(), register3)
}

fun Instruction.shr(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    val r1 = register1.value
    val r2 = register2.value
    return WriteBackOutput((r1.toInt() shr r2.toInt()).toShort(), register3)
}

fun Instruction.and(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    val r1 = register1.value
    val r2 = register2.value
    return WriteBackOutput((r1.toInt() and r2.toInt()).toShort(), register3)
}

fun Instruction.or(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    val r1 = register1.value
    val r2 = register2.value
    return WriteBackOutput((r1.toInt() or r2.toInt()).toShort(), register3)
}

fun Instruction.xor(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    val r1 = register1.value
    val r2 = register2.value
    return WriteBackOutput((r1.toInt() xor r2.toInt()).toShort(), register3)
}

fun Instruction.not(register1: RegisterValue, register2: RegisterAddress): WriteBackOutput {
    useful
    val r1 = register1.value
    return WriteBackOutput((r1.toInt().inv()).toShort(), register2)
}