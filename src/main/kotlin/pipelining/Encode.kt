package org.cuttlefish.pipelining

import org.cuttlefish.Parser.toRegister
import org.cuttlefish.instructions.Instruction
import org.cuttlefish.instructions.InstructionType

class Encode(val instructStr: String) {
    val name = instructStr.split(" ")[0]
    var full: UShort = 0u
    var full2: UShort = 0u

    init {
        opcode()
        when (Instruction.mappings[name]!![2] as InstructionType) {
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
                half(0)
                immediate(instructStr.split(' ')[2].toShort())
            }

            InstructionType.StandAlone -> {}
            InstructionType.RegisterImmediates -> {}
        }


//        register1()
//        register2()
    }

    fun opcode() {
        val code = (Instruction.mappings[name]!![1] as Number).toByte()
        full = code.toUShort()
        full = (full.toInt() shl 16 - 5).toUShort()
    }

    fun half(half: Byte) {
        val shift = (half.toInt() shl 16 - 5) shr 1
        full = (full.toInt() or shift).toUShort()
    }

    fun immediate(number: Short) {
        val shift = ((number.toInt() shl 16 - 5) shr 9) shr 5 + 1
        full = (full.toInt() or shift).toUShort()
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
        val ordinal = instructStr.split(' ')[2].toRegister().ordinal
        val shift = (((ordinal shl 16 - 5) shr 3) shr 3) shr 3
        full = (full.toInt() or shift).toUShort()
    }

}

fun main() {
    val sh = Short.MAX_VALUE / 2 - 3000
    val x = Encode("li r8 $sh") // 11111110000
    println(x.full.toString(2).padStart(16, '0'))
    println(x.full)
}