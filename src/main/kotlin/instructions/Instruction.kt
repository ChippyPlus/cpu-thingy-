package org.cuttlefish.instructions

object Instruction {
    val useful = null
    val mappings = mapOf(
        "push" to listOf(::push, 0),
        "pop" to listOf(::pop, 1),
        "j" to listOf(::j, 2),
        "jnz" to listOf(::jnz, 3),
        "jz" to listOf(::jz, 4),
        "eq" to listOf(::eq, 5),
        "neq" to listOf(::neq, 6),
        "lt" to listOf(::lt, 7),
        "gt" to listOf(::gt, 8),
        "lte" to listOf(::lte, 9),
        "gte" to listOf(::gte, 10),
        "shl" to listOf(::shl, 11),
        "shr" to listOf(::shr, 12),
        "and" to listOf(::and, 13),
        "or" to listOf(::or, 14),
        "xor" to listOf(::xor, 15),
        "not" to listOf(::not, 16),
        "add" to listOf(::add, 17),
        "sub" to listOf(::sub, 18),
        "mul" to listOf(::mul, 19),
        "div" to listOf(::div, 20),
        "mov" to listOf(::mov, 21)
    )
}