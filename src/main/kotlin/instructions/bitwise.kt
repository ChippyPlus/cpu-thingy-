package org.cuttlefish.instructions

// shl,shr,and,or,xor,not

import org.cuttlefish.data.Alu.alu_and
import org.cuttlefish.data.Alu.alu_not
import org.cuttlefish.data.Alu.alu_or
import org.cuttlefish.data.Alu.alu_shl
import org.cuttlefish.data.Alu.alu_shr
import org.cuttlefish.data.Alu.alu_xor
import org.cuttlefish.data.RegisterAddress
import org.cuttlefish.data.RegisterValue
import org.cuttlefish.data.WriteBackOutput_old

suspend fun Instruction.shl(
    register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress
): WriteBackOutput_old {
    useful
    val result = alu_shl(register1.value, register2.value)
    return WriteBackOutput_old(result, register3)
}

suspend fun Instruction.shr(
    register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress
): WriteBackOutput_old {
    useful
    val result = alu_shr(register1.value, register2.value)
    return WriteBackOutput_old(result, register3)
}

suspend fun Instruction.and(
    register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress
): WriteBackOutput_old {
    useful
    val result = alu_and(register1.value, register2.value)
    return WriteBackOutput_old(result, register3)
}

suspend fun Instruction.or(
    register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress
): WriteBackOutput_old {
    useful
    val result = alu_or(register1.value, register2.value)
    return WriteBackOutput_old(result, register3)
}

suspend fun Instruction.xor(
    register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress
): WriteBackOutput_old {
    useful
    val result = alu_xor(register1.value, register2.value)
    return WriteBackOutput_old(result, register3)
}

suspend fun Instruction.not(register1: RegisterValue, register2: RegisterAddress): WriteBackOutput_old {
    useful
    val result = alu_not(register1.value)
    return WriteBackOutput_old(result, register2)
}

