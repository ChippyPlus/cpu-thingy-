package org.cuttlefish

import org.cuttlefish.data.Register
import org.cuttlefish.instructions.Instruction.mappings
import org.cuttlefish.instructions.InstructionType

class Encode(val instructStr: String) {
    val name = instructStr.split(" ")[0]
    var full: Short = 0
    var full2: Short? = null

    init {
        opcode()
        when (mappings[name]!![3] as InstructionType) {
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
                immediate(instructStr.split(' ')[if (name == "j") 1 else 2].toShort())
                opcodeOther()
                halfOther(0)
                immediateOther(instructStr.split(' ')[if (name == "j") 1 else 2].toShort())
            }

            InstructionType.StandAlone -> {}
            InstructionType.RegisterImmediates -> {
                register1()
                val number = instructStr.split(' ')[2].toShort()
                val shift = (number.toInt() and 0xFF)
                full = (full.toInt() or shift).toShort()
                opcodeOther()
                halfOther(0)
                immediateOther(number)
            }
        }


//        register1()
//        register2()
    }

    fun opcode() {
        val code = (mappings[name]!![2] as Number).toByte()
        full = code.toShort()
        full = (full.toInt() shl 16 - 5).toShort()
    }

    fun opcodeOther() {
        val code = (mappings[name]!![2] as Number).toByte()
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
        val ordinal = instructStr.split(' ')[3].toRegister().ordinal
        val shift = (((ordinal shl 16 - 5) shr 3) shr 3) shr 3
        full = (full.toInt() or shift).toShort()
    }

}

//val opcodeMap = mappings.values.associate { (it[1] as Number).toInt() to (it[2] as InstructionType) }
//val opcodeMapFullMeta = mappings.entries.associate { (it.value[1] as Number).toInt() to listOf(it.key, it.value) }

/** Debugging */
@Suppress("unused")
fun Short.bin() = this.toString(2).padStart(16, '0')
fun String.toRegister(): Register {
    return Register.valueOf(this.uppercase())
}