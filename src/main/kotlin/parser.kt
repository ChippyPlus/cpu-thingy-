package org.cuttlefish

import org.cuttlefish.data.Register
import org.cuttlefish.instructions.*

object Parser {
    fun parse(input: String) {
        input.lineSequence().forEach { line ->
            val trimmed = line.trim()
            if (trimmed.isNotEmpty()) {
                fetchEncode(trimmed)
            }
        }
    }

    fun fetchEncode(line: String) {
        val parts = line.split("\\s+".toRegex())
        val command = parts[0]
        val args = parts.drop(1)

        with(Instruction) {
            when (command) {
                "push" -> push(args[0].toRegister())
                "pop" -> pop(args[0].toRegister())
                "j" -> j(args[0].toUInt())
                "jnz" -> jnz(args[0].toUInt(), args[1].toRegister())
                "jz" -> jz(args[0].toUInt(), args[1].toRegister())
                "eq" -> eq(args[0].toRegister(), args[1].toRegister(), args[2].toRegister())
                "neq" -> neq(args[0].toRegister(), args[1].toRegister(), args[2].toRegister())
                "lt" -> lt(args[0].toRegister(), args[1].toRegister(), args[2].toRegister())
                "gt" -> gt(args[0].toRegister(), args[1].toRegister(), args[2].toRegister())
                "lte" -> lte(args[0].toRegister(), args[1].toRegister(), args[2].toRegister())
                "gte" -> gte(args[0].toRegister(), args[1].toRegister(), args[2].toRegister())
                "shl" -> shl(args[0].toRegister(), args[1].toRegister(), args[2].toRegister())
                "shr" -> shr(args[0].toRegister(), args[1].toRegister(), args[2].toRegister())
                "and" -> and(args[0].toRegister(), args[1].toRegister(), args[2].toRegister())
                "or" -> or(args[0].toRegister(), args[1].toRegister(), args[2].toRegister())
                "xor" -> xor(args[0].toRegister(), args[1].toRegister(), args[2].toRegister())
                "not" -> not(args[0].toRegister(), args[1].toRegister())
                "add" -> add(args[0].toRegister(), args[1].toRegister(), args[2].toRegister())
                "sub" -> sub(args[0].toRegister(), args[1].toRegister(), args[2].toRegister())
                "mul" -> mul(args[0].toRegister(), args[1].toRegister(), args[2].toRegister())
                "div" -> div(args[0].toRegister(), args[1].toRegister(), args[2].toRegister())
                "mov" -> mov(args[0].toRegister(), args[1].toRegister())
                "li" -> li(args[0].toRegister(), args[1].toShort())
                "swp" -> swp(args[0].toRegister(), args[1].toRegister())
                "pr" -> println(args[0].toRegister().read())
                else -> throw IllegalArgumentException("Unknown instruction: $command")
            }
        }
    }

    private fun String.toRegister(): Register {
        return Register.valueOf(this.uppercase())
    }
}
