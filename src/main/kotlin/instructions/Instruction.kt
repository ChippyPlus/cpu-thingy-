package org.cuttlefish.instructions

object Instruction {
    val useful = null
    val mappings = mapOf(
        "push" to listOf(/*::push */ null, 0, InstructionType.Register1),
        "pop" to listOf(/*::pop*/ null, 1, InstructionType.Register1),
        "j" to listOf(/*::j*/ null, 2, InstructionType.Immediates),
        "jnz" to listOf(/*::jnz*/ null, 3, InstructionType.RegisterImmediates),
        "jz" to listOf(/*::jz*/ null, 4, InstructionType.RegisterImmediates),
        "eq" to listOf(/*::eq*/ null, 5, InstructionType.Register3),
        "neq" to listOf(/*::neq*/ null, 6, InstructionType.Register3),
        "lt" to listOf(/*::lt*/ null, 7, InstructionType.Register3),
        "gt" to listOf(/*::gt*/ null, 8, InstructionType.Register3),
        "lte" to listOf(/*::lte*/ null, 9, InstructionType.Register3),
        "gte" to listOf(/*::gte*/ null, 10, InstructionType.Register3),
        "shl" to listOf(/*::shl*/ null, 11, InstructionType.Register3),
        "shr" to listOf(/*::shr*/ null, 12, InstructionType.Register3),
        "and" to listOf(/*::and*/ null, 13, InstructionType.Register3),
        "or" to listOf(/*::or*/ null, 14, InstructionType.Register3),
        "xor" to listOf(/*::xor*/ null, 15, InstructionType.Register3),
        "not" to listOf(/*::not*/ null, 16, InstructionType.Register2),
        "add" to listOf(/*::add*/ null, 17, InstructionType.Register3),
        "sub" to listOf(/*::sub*/ null, 18, InstructionType.Register3),
        "mul" to listOf(/*::mul*/ null, 19, InstructionType.Register3),
        "div" to listOf(/*::div*/ null, 20, InstructionType.Register3),
        "mov" to listOf(/*::mov*/ null, 21, InstructionType.Register2),
        "li" to listOf(/*::li*/ null, 23, InstructionType.RegisterImmediates),
        "pr" to listOf(/*::pr*/ null, 24, InstructionType.Register1),
        "ld" to listOf(/*::load*/ null, 25, InstructionType.Register2),
        "st" to listOf(/*::store*/ null, 26, InstructionType.Register2)
    )
}