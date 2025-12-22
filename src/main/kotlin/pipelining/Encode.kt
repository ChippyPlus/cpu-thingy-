package org.cuttlefish.pipelining

import org.cuttlefish.Parser.toRegister
import org.cuttlefish.instructions.Instruction

class Encode(val instructStr: String) {
    val name = instructStr.split(" ")[0]
    var full: UShort = 0u

    init {
        opcode()
        register1()
        register2()
    }

    fun opcode() {
        val code = (Instruction.mappings[name]!![1] as Number).toByte()
        full = code.toUShort()
        full = (full.toInt() shl 16 - 5 ).toUShort()
    }

    fun register1() {
        val ordinal = instructStr.split(' ')[1].toRegister().ordinal
        val shift = (ordinal shl 16 - 5 ) shr 3
        full = (full.toInt() or shift).toUShort()
//        full = (full.toInt() shl 16 - 5 + 1).toUShort()
    }

    fun register2() {
        val ordinal = instructStr.split(' ')[2].toRegister().ordinal
        val shift = ((ordinal shl 16 - 5) shr 3) shr 3
        full = (full.toInt() or shift).toUShort()
//        full = (full.toInt() shl 16 - 5 + 1).toUShort()
    }

}

fun main() {
    val x = Encode("shr r8 r2")
    println(x.full.toString(2).padStart(16, '0'))
    println(x.full)
}