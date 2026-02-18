package org.cuttlefish.instructions


// j, jnz, jz

import org.cuttlefish.data.Register
import org.cuttlefish.data.RegisterValue


suspend fun Instruction.j(line: UShort) {
    useful
    Register.PC.writePrivilege(line)
}

suspend fun Instruction.jnz(line: UShort, register: RegisterValue) {
    useful
    if (register.value != 0.toShort()) {
        Register.PC.writePrivilege(line)
    }
}

suspend fun Instruction.jz(line: UShort, register: RegisterValue) {
    useful
    if (register.value == 0.toShort()) {
        Register.PC.writePrivilege(line)
    }
}
