package org.cuttlefish

import org.cuttlefish.data.MemoryAddress
import org.cuttlefish.data.RegisterAddress
import org.cuttlefish.instructions.InstructionType
import org.cuttlefish.util.Maybe


data class DataFlow(
    var p1if: List<UShort?>? = null,
    val decode: Maybe<DecodeOutput> = Maybe.None,
    val aluResult: Maybe<AluResult> = Maybe.None,
    val writeBack: Maybe<WriteBackOutput> = Maybe.None,
    val memWrite: Maybe<MemWriteOutput> = Maybe.None
)

data class DecodeOutput(
    val name: String,
    val full1: UShort,
    val full2: UShort?,
    val format: InstructionType,
    val structure: List<Any>,
    val registerStructure: List<Any>
)

data class WriteBackOutput(
    val value: Short, val location: RegisterAddress
)

sealed interface AluResult {
    data class Register(val returns: WriteBackOutput) : AluResult
    data class Memory(val returns: MemWriteOutput) : AluResult
    object Empty : AluResult
}

data class MemWriteOutput(
    val value: Short, val location: MemoryAddress, val opName: Char, val optionalRegisterLocation: RegisterAddress?
)