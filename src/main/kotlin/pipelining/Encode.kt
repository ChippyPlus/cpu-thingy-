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
    while (Register.PC.readToPc().toInt() < index) {
        val full1 = memory[Register.PC.readToPc().toInt()].toUShort()
        val opcode = full1.toInt() shr (16 - 5)
        val type = opcodeMap[opcode]
        val full2: UShort?
        if (type == InstructionType.Immediates || type == InstructionType.RegisterImmediates) {
            Register.PC.writeToPc((Register.PC.readToPc() + 1u).toUShort())
            full2 = memory[Register.PC.readToPc().toInt()].toUShort()
        } else {
            full2 = null
        }
        println(ID(full1, full2).fmt())
        Register.PC.writeToPc((Register.PC.readToPc() + 1u).toUShort())
    }
}

/** Debugging */
@Suppress("unused")
fun UShort.bin() = this.toString(2).padStart(16, '0')
