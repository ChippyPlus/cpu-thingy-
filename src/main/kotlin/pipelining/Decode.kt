package org.cuttlefish.pipelining

import org.cuttlefish.data.Memory
import org.cuttlefish.data.Register
import org.cuttlefish.instructions.Instruction.mappings
import org.cuttlefish.instructions.InstructionType


class Decode {

    var instr = Register.INSTR.read().toUShort()


    val name = name()
    val fmt = mappings[name]!![2] as InstructionType
    fun name(): String {
        val number = (instr.toInt() shr 16 - 5).toUShort()
        return mappings.entries.map { it.value[1] to it.key }.find { it.first == number.toInt() }!!.second
    }

    fun fmtStructure(): List<Any> {
        when (mappings[name]!![2] as InstructionType) {
            InstructionType.Register1 -> {
                val removedOpcode = (instr.toInt() shl 5).toUShort()
                val shift = (removedOpcode.toInt() shr 16 - 3).toUShort()
                return listOf(name, Register.entries[shift.toInt()]) // the only unique data
            }

            InstructionType.Register3 -> {
                val removedOpcode = (instr.toInt() shl 5).toUShort()
                val shift1 = (removedOpcode.toInt() shr 16 - 3).toUShort()
                val shift2 = ((removedOpcode.toInt() shl 3).toUShort().toInt() shr 16 - 3).toUShort()
                val shift3 = ((removedOpcode.toInt() shl 6).toUShort().toInt() shr 16 - 3).toUShort()
                return listOf(
                    name,
                    Register.entries[shift1.toInt()],
                    Register.entries[shift2.toInt()],
                    Register.entries[shift3.toInt()]
                )
            }

            InstructionType.Register2 -> {
                val removedOpcode = (instr.toInt() shl 5).toUShort()
                val shift1 = (removedOpcode.toInt() shr 16 - 3).toUShort()
                val shift2 = ((removedOpcode.toInt() shl 3).toUShort().toInt() shr 16 - 3).toUShort()
                return listOf(
                    name, Register.entries[shift1.toInt()], Register.entries[shift2.toInt()]
                )
            }

            InstructionType.Immediates -> {


                val removedOpcode1 = (instr.toInt() shl 5).toUShort()
                val half1 = (removedOpcode1.toInt() shr 16 - 1).toUShort()
                val halfImmediate1 = (instr.toInt() shl 6).toUShort().toInt() shr 8
                val immediate1 = if (half1.toUInt() == 1u) {
                    halfImmediate1.toShort()
                } else if (half1.toUInt() == 0u) {
                    (halfImmediate1.toShort().toInt() shl 8).toShort()
                } else throw IllegalStateException("Half ≠ 0 or 1, impossible?")

                Register.PC.inc()
                Register.INSTR.write(Memory.read(Register.PC.read()))
                instr = Register.INSTR.read().toUShort()

                val removedOpcode2 = (instr.toInt() shl 5).toUShort()
                val half2 = (removedOpcode2.toInt() shr 16 - 1).toUShort()
                val halfImmediate2 = (instr.toInt() shl 6).toUShort().toInt() shr 8
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
                val halfImmediate1 = (instr.toInt() shl 8).toUShort().toInt() shr 8
                val imm1 = halfImmediate1.toShort()
                val register =
                    Register.entries[((instr.toInt() shl 5).toUShort().toInt() shr 16 - 3).toUShort().toInt()]

                Register.PC.inc()
                Register.INSTR.write(Memory.read(Register.PC.read()))
                instr = Register.INSTR.read().toUShort()

                val removedOpcode2 = (instr.toInt() shl 5).toUShort()
                val half2 = (removedOpcode2.toInt() shr 16 - 1).toUShort()
                val halfImmediate2 = (instr.toInt() shl 6).toUShort().toInt() shr 8
                val immediate2 = if (half2.toUInt() == 1u) {
                    halfImmediate2.toShort()
                } else if (half2.toUInt() == 0u) {
                    (halfImmediate2.toShort().toInt() shl 8).toShort()
                } else throw IllegalStateException("Half ≠ 0 or 1, impossible?")

                return listOf(name, register, (imm1.toInt() or immediate2.toInt()).toUShort())
            }
        }
    }

//    fun fillInRegisters(): List<Any> {
//        val structure = fmtStructure()
//        val newStructure = mutableListOf<Any>()
//        newStructure.add(structure[0])
//        when (fmt) {
//            InstructionType.Register1 -> {
//                if (name == "pr" || name == "push") {
//                    newStructure.add(RegisterValue((structure[1] as Register).read()))
//                } else {
//                    newStructure.add(RegisterAddress((structure[1] as Register)))
//                }
//            }
//
//            InstructionType.Register3 -> {
//                newStructure.add(RegisterValue((structure[1] as Register).read()))
//                newStructure.add(RegisterValue((structure[2] as Register).read()))
//                newStructure.add(RegisterAddress((structure[3] as Register)))
//            }
//
//            InstructionType.Register2 -> {
//                newStructure.add(RegisterValue((structure[1] as Register).read()))
//                if (name == "st") {
//                    newStructure.add(RegisterValue((structure[2] as Register).read()))
//                } else {
//                    newStructure.add(RegisterAddress((structure[2] as Register)))
//                }
//            }
//
//            InstructionType.Immediates -> {
//                newStructure.add((structure[1] as UShort))
//            }
//
//            InstructionType.StandAlone -> {
//
//            }
//
//            InstructionType.RegisterImmediates -> {
//
//                if (name == "li") {
//                    newStructure.add(RegisterAddress((structure[1] as Register)))
//                } else {
//                    newStructure.add(RegisterValue((structure[1] as Register).read()))
//                }
//
//                newStructure.add((structure[2] as UShort).toShort())
//
//            }
//        }
//
//        return newStructure
//
//    }

    fun decode(): List<Any> {
        println("decoding")
        return fmtStructure()
    }

}
