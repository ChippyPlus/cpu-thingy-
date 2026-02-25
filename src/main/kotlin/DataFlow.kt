package org.cuttlefish

import org.cuttlefish.data.MemoryAddress
import org.cuttlefish.data.RegisterAddress
import org.cuttlefish.instructions.InstructionType
import org.cuttlefish.util.Maybe


data class DataFlow(
    val decode: Maybe<DecodeOutput> = Maybe.None,
    val writeBack: Maybe<WriteBackOutput> = Maybe.None,
    val memWrite: Maybe<MemWriteOutput> = Maybe.None
)

data class DecodeOutput(
    val name: String,
    val full1: UShort,
    val full2: UShort?,
    val full3: UShort?,
    val format: InstructionType,
    val structure: List<Any>,
    val registerStructure: List<Any>
)

data class WriteBackOutput(
    val value: Short,
    val location: RegisterAddress
)

data class MemWriteOutput(
    val value: Short,
    val location: MemoryAddress,
    val opName: Char,
    val optionalRegisterLocation: RegisterAddress?
)