package org.cuttlefish.pipelining

import org.cuttlefish.data.Memory
import org.cuttlefish.data.Register
import org.cuttlefish.instructions.Instruction.mappings
import org.cuttlefish.instructions.InstructionType

/**
 * 1. Fetch instruction from memory
 */
class IF {
    private val opcodeMap = mappings.values.associate { (it[1] as Number).toInt() to (it[2] as InstructionType) }

    fun fetch(): List<UShort?> {
        val pc = Register.PC.readToPc()
        val full1 = Memory.read(pc.toShort()).toUShort()

        val opcode = full1.toInt() shr (16 - 5)
        val type = opcodeMap[opcode]

        Register.PC.writeToPc((pc + 1u).toUShort())

        var full2: UShort? = null
        if (type == InstructionType.Immediates || type == InstructionType.RegisterImmediates) {
            val nextPc = Register.PC.readToPc()
            full2 = Memory.read(nextPc.toShort()).toUShort()
            Register.PC.writeToPc((nextPc + 1u).toUShort())
        }

        return listOf(full1, full2)
    }
}
