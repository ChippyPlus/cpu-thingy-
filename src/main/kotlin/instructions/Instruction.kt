package org.cuttlefish.instructions

object Instruction {
    val useful = null
    val mappings = mapOf(
        "push" to listOf(::push, 0, InstructionType.Register1),
        "pop" to listOf(::pop, 1, InstructionType.Register1),
        "j" to listOf(::j, 2, InstructionType.Immediates),
        "jnz" to listOf(::jnz, 3, InstructionType.RegisterImmediates),
        "jz" to listOf(::jz, 4, InstructionType.RegisterImmediates),
        "eq" to listOf(::eq, 5, InstructionType.Register3),
        "neq" to listOf(::neq, 6, InstructionType.Register3),
        "lt" to listOf(::lt, 7, InstructionType.Register3),
        "gt" to listOf(::gt, 8, InstructionType.Register3),
        "lte" to listOf(::lte, 9, InstructionType.Register3),
        "gte" to listOf(::gte, 10, InstructionType.Register3),
        "shl" to listOf(::shl, 11, InstructionType.Register3),
        "shr" to listOf(::shr, 12, InstructionType.Register3),
        "and" to listOf(::and, 13, InstructionType.Register3),
        "or" to listOf(::or, 14, InstructionType.Register3),
        "xor" to listOf(::xor, 15, InstructionType.Register3),
        "not" to listOf(::not, 16, InstructionType.Register2),
        "add" to listOf(::add, 17, InstructionType.Register3),
        "sub" to listOf(::sub, 18, InstructionType.Register3),
        "mul" to listOf(::mul, 19, InstructionType.Register3),
        "div" to listOf(::div, 20, InstructionType.Register3),
        "mov" to listOf(::mov, 21, InstructionType.Register2),
        "li" to listOf(::li, 23, InstructionType.RegisterImmediates),
        "pr" to listOf(::pr, 24, InstructionType.Register1),
        "ld" to listOf(::load, 25, InstructionType.Register2),
        "st" to listOf(::store, 26, InstructionType.Register2)


    )
}