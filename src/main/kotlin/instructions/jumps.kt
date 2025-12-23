package org.cuttlefish.instructions

import org.cuttlefish.data.Register
import org.cuttlefish.data.RegisterValue


fun Instruction.j(line: UShort) {
    useful
    Register.PC.writePrivilege(line)
}

fun Instruction.jnz(line: UShort, register: RegisterValue) {
    useful
    if (register.value != 0.toShort()) {
        Register.PC.writePrivilege(line)
    }
}

fun Instruction.jz(line: UShort, register: RegisterValue) {
    useful
    if (register.value == 0.toShort()) {
        Register.PC.writePrivilege(line)
    }
}
