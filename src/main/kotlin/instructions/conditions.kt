package org.cuttlefish.instructions

// eq,neq,lt,lte

import org.cuttlefish.data.Alu.alu_eq
import org.cuttlefish.data.Alu.alu_gt
import org.cuttlefish.data.Alu.alu_gte
import org.cuttlefish.data.Alu.alu_lt
import org.cuttlefish.data.Alu.alu_lte
import org.cuttlefish.data.Alu.alu_neq
import org.cuttlefish.data.RegisterAddress
import org.cuttlefish.data.RegisterValue
import org.cuttlefish.data.WriteBackOutput


suspend fun Instruction.eq(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    val result = alu_eq(register1.value, register2.value)
    return WriteBackOutput(result, register3)
}

suspend fun Instruction.neq(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    val result = alu_neq(register1.value, register2.value)
    return WriteBackOutput(result, register3)
}

suspend fun Instruction.lt(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    val result = alu_lt(register1.value, register2.value)
    return WriteBackOutput(result, register3)
}

suspend fun Instruction.gt(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    val result = alu_gt(register1.value, register2.value)
    return WriteBackOutput(result, register3)
}

suspend fun Instruction.lte(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    val result = alu_lte(register1.value, register2.value)
    return WriteBackOutput(result, register3)
}

suspend fun Instruction.gte(register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress): WriteBackOutput {
    useful
    val result = alu_gte(register1.value, register2.value)
    return WriteBackOutput(result, register3)
}