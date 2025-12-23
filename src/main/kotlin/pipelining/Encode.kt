package org.cuttlefish.pipelining

import org.cuttlefish.Parser.toRegister
import org.cuttlefish.data.Register
import org.cuttlefish.instructions.Instruction.mappings
import org.cuttlefish.instructions.InstructionType
import java.io.File

class Encode(val instructStr: String) {
    val name = instructStr.split(" ")[0]
    var full: UShort = 0u
    var full2: UShort? = null

    init {
        opcode()
        when (mappings[name]!![2] as InstructionType) {
            InstructionType.Register1 -> {
                register1()
            }

            InstructionType.Register2 -> {
                register1()
                register2()
            }

            InstructionType.Register3 -> {
                register1()
                register2()
                register3()
            }

            InstructionType.Immediates -> {
                full2 = 0u
                half(1)
                immediate(instructStr.split(' ')[2].toShort())
                opcodeOther()
                halfOther(0)
                immediateOther(instructStr.split(' ')[2].toShort())
            }

            InstructionType.StandAlone -> {}
            InstructionType.RegisterImmediates -> {
                register1()
                val number = instructStr.split(' ')[2].toShort()
                val shift = (number.toInt() and 0xFF)
                full = (full.toInt() or shift).toUShort()
                opcodeOther()
                halfOther(0)
                immediateOther(number)
            }
        }


//        register1()
//        register2()
    }

    fun opcode() {
        val code = (mappings[name]!![1] as Number).toByte()
        full = code.toUShort()
        full = (full.toInt() shl 16 - 5).toUShort()
    }

    fun opcodeOther() {
        val code = (mappings[name]!![1] as Number).toByte()
        full2 = code.toUShort()
        full2 = (full2!!.toInt() shl 16 - 5).toUShort()
    }

    fun half(half: Byte) {
        val shift = (half.toInt() shl 16 - 5) shr 1
        full = (full.toInt() or shift).toUShort()
    }

    fun halfOther(half: Byte) {
        val shift = (half.toInt() shl 16 - 5) shr 1
        full2 = (full2!!.toInt() or shift).toUShort()
    }


    fun immediate(number: Short) {
        val shift = (number.toInt() and 0xFF) shl 2
        full = (full.toInt() or shift).toUShort()
    }

    fun immediateOther(number: Short) {
        val shift = ((number.toInt() shr 8) and 0xFF) shl 2
        full2 = (full2!!.toInt() or shift).toUShort()
    }


    fun register1() {
        val ordinal = instructStr.split(' ')[1].toRegister().ordinal
        val shift = (ordinal shl 16 - 5) shr 3
        full = (full.toInt() or shift).toUShort()
    }

    fun register2() {
        val ordinal = instructStr.split(' ')[2].toRegister().ordinal
        val shift = ((ordinal shl 16 - 5) shr 3) shr 3
        full = (full.toInt() or shift).toUShort()
    }

    fun register3() {
        val ordinal = instructStr.split(' ')[3].toRegister().ordinal
        val shift = (((ordinal shl 16 - 5) shr 3) shr 3) shr 3
        full = (full.toInt() or shift).toUShort()
    }

}

class Decode(val full1: UShort, val full2: UShort? = null) {
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
                return listOf(name,Register.entries[shift.toInt()]) // the only unique data
            }

            InstructionType.Register3 -> {
                val removedOpcode = (full1.toInt() shl 5).toUShort()
                val shift1 = (removedOpcode.toInt() shr 16 - 3).toUShort()
                val shift2 = ((removedOpcode.toInt() shl 3).toUShort().toInt() shr 16 - 3).toUShort()
                val shift3 = ((removedOpcode.toInt() shl 6).toUShort().toInt() shr 16 - 3).toUShort()
                return listOf(name,
                    Register.entries[shift1.toInt()], Register.entries[shift2.toInt()], Register.entries[shift3.toInt()]
                )
            }

            InstructionType.Register2 -> {
                val removedOpcode = (full1.toInt() shl 5).toUShort()
                val shift1 = (removedOpcode.toInt() shr 16 - 3).toUShort()
                val shift2 = ((removedOpcode.toInt() shl 3).toUShort().toInt() shr 16 - 3).toUShort()
                return listOf(name,
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





                return listOf(name,(immediate2.toInt() or immediate1.toInt()).toUShort())
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

                return listOf(name,register, (imm1.toInt() or immediate2.toInt()).toUShort())
            }
        }
    }


}


fun main() {
    val memory = ShortArray(64) { 0 }
    var index = 0
    for (line in File("main.lx").readLines()) {
        val encode = Encode(line)
        memory[index] = encode.full.toShort()
        index++
        if (encode.full2 != null) {
            memory[index] = encode.full2!!.toShort()
            index++
        }
    }

    val opcodeMap = mappings.values.associate { (it[1] as Number).toInt() to (it[2] as InstructionType) }
    var pc = 0
    while (pc < index) {
        val full1 = memory[pc].toUShort()
        val opcode = full1.toInt() shr (16 - 5)
        val type = opcodeMap[opcode]
        val full2: UShort?
        if (type == InstructionType.Immediates || type == InstructionType.RegisterImmediates) {
            pc++
            full2 = memory[pc].toUShort()
        } else {
            full2 = null
        }
        println(Decode(full1, full2).fmt())
        pc++
    }
}

/** Debugging */
@Suppress("unused")
fun UShort.bin() = this.toString(2).padStart(16, '0')
