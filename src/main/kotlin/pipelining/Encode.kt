package org.cuttlefish.pipelining

import org.cuttlefish.Parser.toRegister
import org.cuttlefish.instructions.Instruction
import org.cuttlefish.instructions.InstructionType
import java.io.File
import kotlin.math.pow
import kotlin.text.toString

class Encode(val instructStr: String) {
    val name = instructStr.split(" ")[0]
    var full: Short = 0
    var full2: Short? = null

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
                full2 = 0
                half(1)
                immediate(instructStr.split(' ')[2].toShort())
                opcodeOther()
                halfOther(0)
                immediateOther(instructStr.split(' ')[2].toShort())
            }

            InstructionType.StandAlone -> {}
            InstructionType.RegisterImmediates -> {}
        }


//        register1()
//        register2()
    }

    fun opcode() {
        val code = (Instruction.mappings[name]!![1] as Number).toByte()
        full = code.toShort()
        full = (full.toInt() shl 16 - 5).toShort()
    }
    fun opcodeOther() {
        val code = (Instruction.mappings[name]!![1] as Number).toByte()
        full2 = code.toShort()
        full2 = (full2!!.toInt() shl 16 - 5).toShort()
    }

    fun half(half: Byte) {
        val shift = (half.toInt() shl 16 - 5) shr 1
        full = (full.toInt() or shift).toShort()
    }
    fun halfOther(half: Byte) {
        val shift = (half.toInt() shl 16 - 5) shr 1
        full2 = (full2!!.toInt() or shift).toShort()
    }


    fun immediate(number: Short) {
        val shift = (number.toInt() and 0xFF) shl 2
        full = (full.toInt() or shift).toShort()
    }
    fun immediateOther(number: Short) {
        val shift = ((number.toInt() shr 8) and 0xFF) shl 2
        full2 = (full2!!.toInt() or shift).toShort()
    }


    fun register1() {
        val ordinal = instructStr.split(' ')[1].toRegister().ordinal
        val shift = (ordinal shl 16 - 5) shr 3
        full = (full.toInt() or shift).toShort()
    }

    fun register2() {
        val ordinal = instructStr.split(' ')[2].toRegister().ordinal
        val shift = ((ordinal shl 16 - 5) shr 3) shr 3
        full = (full.toInt() or shift).toShort()
    }

    fun register3() {
        val ordinal = instructStr.split(' ')[2].toRegister().ordinal
        val shift = (((ordinal shl 16 - 5) shr 3) shr 3) shr 3
        full = (full.toInt() or shift).toShort()
    }

}

fun main() {
    val memory = ShortArray(64) {0}
    
//    val sh = Short.MAX_VALUE / 2-3000 //11111111111111
//    val x = Encode("li r8 $sh") // 11111110000
//    println(x.full.toString(2).padStart(16, '0').removeRange(0..5).removeRange(8,10))
//    println(x.full2!!.toString(2).padStart(16, '0').removeRange(0..5).removeRange(8,10))
//    println("0011010001000111".toInt(2))
//    println(sh)
    val text = File("main.lx").readLines()
    for (line in text) {
        val encode = Encode(line)
        println("$line 1 : ${encode.full.toString(2).padStart(16, '0')}")
        println(encode.full2)
        if (encode.full2 != null) {
            println("$line 2 : ${encode.full2!!.toString(2).padStart(16, '0')}")

        }
    }
    

}