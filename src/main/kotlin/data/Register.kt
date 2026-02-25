package org.cuttlefish.data

import kotlinx.coroutines.delay
import org.cuttlefish.instructions.InstructionType

enum class Register {
    R1, R2, R3, R4, PC, INSTR;

    private var data: Short = 0
    private var dataSpecial: UShort = 0u
    suspend fun read(): Short {
        if (this == PC || this == INSTR) throw IllegalStateException("Reading from Privileged registers is not allowed!!!!")
        delay(Clock.SPEED_REGISTER_READ)
        return data
    }

    suspend fun write(data: Short) {
        delay(Clock.SPEED_REGISTER_WRITE)
        if (this == PC || this == INSTR) throw IllegalStateException("Writing to Privileged registers is not allowed!!!!")
        this.data = data
    }

    suspend fun readPrivilege(): UShort {
        delay(Clock.SPEED_REGISTER_READ)
        if (this == PC || this == INSTR) return dataSpecial
        else throw IllegalStateException("This is only for the PRIVILEGED REGISTERS!!!!!")
    }

    suspend fun writePrivilege(data: UShort) {
        delay(Clock.SPEED_REGISTER_WRITE)
        if (this == PC || this == INSTR) dataSpecial = data
        else throw IllegalStateException("This is only for the PRIVILEGED REGISTERS!!!!!")
    }

}

@JvmInline
value class RegisterValue(val value: Short)

@JvmInline
value class RegisterAddress(val value: Register)

@JvmInline
value class MemoryAddress(val value: Short)

@Deprecated("In favour of DataFlow")
data class DecodeInstruction(
    val full1: UShort,
    val full2: UShort?,
    val name: String,
    val format: InstructionType,
    val structure: List<Any>,
    val registerStructure: List<Any>,
)

@Suppress("ClassName")
@Deprecated("In favour of DataFlow")
data class WriteBackOutput_old(
    val value: Short, val location: RegisterAddress
)

@Suppress("ClassName")
@Deprecated("In favour of DataFlow")
data class MEMWruteBackOutput_old(
    val value: Short, val location: MemoryAddress, val opName: Char, val optionalRegisterLocation: RegisterAddress?
)