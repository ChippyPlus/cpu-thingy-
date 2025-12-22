package org.cuttlefish.instructions


// 11

enum class InstructionType {
    /**
     * 5 - Opcode
     * 3 - Register
     * 8 - Nothing
     */
    Register1,

    /**
     * 5 - Opcode
     * 3 - Register
     * 3 - Register
     * 3 - Register
     */
    Register3,
    /**
     * 5 - Opcode
     * 3 - Register
     * 3 - Register
     * 11- Nothing
     */
    Register2,

    /**
     * 5 - Opcode
     * 1 - Half
     * 8 - Immediate
     * 2 - Nothing
     */
    Immediates,

    /**
     * 5 - Opcode
     * 11 - Nothing
     */
    StandAlone,

    /**
     * 5 - Opcode
     * 3 - Register
     * 8 - Immediate
     */
    RegisterImmediates

}

//data class FmtRegister1()