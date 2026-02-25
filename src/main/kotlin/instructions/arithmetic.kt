package org.cuttlefish.instructions

import org.cuttlefish.data.Alu.alu_add
import org.cuttlefish.data.Alu.alu_div
import org.cuttlefish.data.Alu.alu_mul
import org.cuttlefish.data.Alu.alu_sub
import org.cuttlefish.data.RegisterAddress
import org.cuttlefish.data.RegisterValue
import org.cuttlefish.data.WriteBackOutput_old

// add, sub, mul, div

suspend fun Instruction.add(
    register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress
): WriteBackOutput_old {
    useful
    val result = alu_add(register1.value, register2.value)
    return WriteBackOutput_old((result), register3)
}

suspend fun Instruction.sub(
    register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress
): WriteBackOutput_old {
    useful
    val result = alu_sub(register1.value, register2.value)
    return WriteBackOutput_old((result), register3)
}

suspend fun Instruction.mul(
    register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress
): WriteBackOutput_old {
    useful
    val result = alu_mul(register1.value, register2.value)
    return WriteBackOutput_old((result), register3)

}

suspend fun Instruction.div(
    register1: RegisterValue, register2: RegisterValue, register3: RegisterAddress
): WriteBackOutput_old {
    useful
    val result = alu_div(register1.value, register2.value)
    return WriteBackOutput_old((result), register3)
}
