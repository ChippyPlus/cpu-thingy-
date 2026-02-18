package org.cuttlefish.pipelining

import org.cuttlefish.data.Memory
import org.cuttlefish.data.PipeBuffer
import org.cuttlefish.data.Register
import org.cuttlefish.instructions.Instruction.mappings
import org.cuttlefish.instructions.InstructionType

/**
 * 1 Fetch instruction from memory
 */
class IF {
    private val opcodeMap = mappings.values.associate { (it[1] as Number).toInt() to (it[2] as InstructionType) }

    private fun insideFetch(): List<UShort?> {
        val pc = Register.PC.readPrivilege()
        val full1 = Memory.read(pc.toShort()).toUShort()

        val opcode = full1.toInt() shr (16 - 5)
        val type = opcodeMap[opcode]

        Register.PC.writePrivilege((pc + 1u).toUShort())
        Register.INSTR.writePrivilege(full1)

        var full2: UShort? = null
        if (type == InstructionType.Immediates || type == InstructionType.RegisterImmediates) {
            val nextPc = Register.PC.readPrivilege()
            full2 = Memory.read(nextPc.toShort()).toUShort()
            Register.PC.writePrivilege((nextPc + 1u).toUShort())
            Register.INSTR.writePrivilege(full2)
        }

        return listOf(full1, full2)
    }

    fun fetch() {
//        println("Fetching")
        PipeBuffer.pif = insideFetch()
    }
}
