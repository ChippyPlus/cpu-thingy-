package org.cuttlefish.pipelining

import org.cuttlefish.data.Register
import org.cuttlefish.instructions.Instruction.mappings
import org.cuttlefish.instructions.InstructionType


/**
 * 2. Decode instruction and read registers
 */
class ID(val full1: UShort, val full2: UShort? = null) {
    val name = name()

    fun name(): String {
        val number = (full1.toInt() shr 16 - 5).toUShort()
        return mappings.entries.map { it.value[1] to it.key }.find { it.first == number.toInt() }!!.second
    }

    fun fmt(): List<Any> {
        when (mappings[name]!![2] as InstructionType) {
            InstructionType.Register1 -> {
                val removedOpcode = (full1.toInt() shl 5).toUShort()
                val shift = (removedOpcode.toInt() shr 16 - 3).toUShort()
                return listOf(name, Register.entries[shift.toInt()]) // the only unique data
            }

            InstructionType.Register3 -> {
                val removedOpcode = (full1.toInt() shl 5).toUShort()
                val shift1 = (removedOpcode.toInt() shr 16 - 3).toUShort()
                val shift2 = ((removedOpcode.toInt() shl 3).toUShort().toInt() shr 16 - 3).toUShort()
                val shift3 = ((removedOpcode.toInt() shl 6).toUShort().toInt() shr 16 - 3).toUShort()
                return listOf(
                    name,
                    Register.entries[shift1.toInt()], Register.entries[shift2.toInt()], Register.entries[shift3.toInt()]
                )
            }

            InstructionType.Register2 -> {
                val removedOpcode = (full1.toInt() shl 5).toUShort()
                val shift1 = (removedOpcode.toInt() shr 16 - 3).toUShort()
                val shift2 = ((removedOpcode.toInt() shl 3).toUShort().toInt() shr 16 - 3).toUShort()
                return listOf(
                    name,
                    Register.entries[shift1.toInt()], Register.entries[shift2.toInt()]
                )
            }

            InstructionType.Immediates -> {
                val removedOpcode1 = (full1.toInt() shl 5).toUShort()
                val half1 = (removedOpcode1.toInt() shr 16 - 1).toUShort()
                val halfImmediate1 = (full1.toInt() shl 6).toUShort().toInt() shr 8
                val immediate1 = if (half1.toUInt() == 1u) {
                    halfImmediate1.toShort()
                } else if (half1.toUInt() == 0u) {
                    (halfImmediate1.toShort().toInt() shl 8).toShort()
                } else throw IllegalStateException("Half ≠ 0 or 1, impossible?")

                val removedOpcode2 = (full2!!.toInt() shl 5).toUShort()
                val half2 = (removedOpcode2.toInt() shr 16 - 1).toUShort()
                val halfImmediate2 = (full2.toInt() shl 6).toUShort().toInt() shr 8
                val immediate2 = if (half2.toUInt() == 1u) {
                    halfImmediate2.toShort()
                } else if (half2.toUInt() == 0u) {
                    (halfImmediate2.toShort().toInt() shl 8).toShort()
                } else throw IllegalStateException("Half ≠ 0 or 1, impossible?")





                return listOf(name, (immediate2.toInt() or immediate1.toInt()).toUShort())
            }

            InstructionType.StandAlone -> {
                return listOf(name)
            }

            InstructionType.RegisterImmediates -> {
                val halfImmediate1 = (full1.toInt() shl 8).toUShort().toInt() shr 8
                val imm1 = halfImmediate1.toShort()
                val register =
                    Register.entries[((full1.toInt() shl 5).toUShort().toInt() shr 16 - 3).toUShort().toInt()]

                val removedOpcode2 = (full2!!.toInt() shl 5).toUShort()
                val half2 = (removedOpcode2.toInt() shr 16 - 1).toUShort()
                val halfImmediate2 = (full2.toInt() shl 6).toUShort().toInt() shr 8
                val immediate2 = if (half2.toUInt() == 1u) {
                    halfImmediate2.toShort()
                } else if (half2.toUInt() == 0u) {
                    (halfImmediate2.toShort().toInt() shl 8).toShort()
                } else throw IllegalStateException("Half ≠ 0 or 1, impossible?")

                return listOf(name, register, (imm1.toInt() or immediate2.toInt()).toUShort())
            }
        }
    }

}
