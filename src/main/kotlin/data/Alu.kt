package org.cuttlefish.data

import kotlinx.coroutines.delay

@Suppress("FunctionName")
object Alu {

    suspend fun alu_shl(a: Short, b: Short): Short {
        delay(Clock.SPEED_ALU)
        return (a.toInt() shl b.toInt()).toShort()
    }

    suspend fun alu_shr(a: Short, b: Short): Short {
        delay(Clock.SPEED_ALU)
        return (a.toInt() shr b.toInt()).toShort()
    }

    suspend fun alu_and(a: Short, b: Short): Short {
        delay(Clock.SPEED_ALU)
        return (a.toInt() and b.toInt()).toShort()
    }

    suspend fun alu_or(a: Short, b: Short): Short {
        delay(Clock.SPEED_ALU)
        return (a.toInt() or b.toInt()).toShort()
    }

    suspend fun alu_xor(a: Short, b: Short): Short {
        delay(Clock.SPEED_ALU)
        return (a.toInt() xor b.toInt()).toShort()
    }

    suspend fun alu_not(a: Short): Short {
        delay(Clock.SPEED_ALU)
        return a.toInt().inv().toShort()
    }

    suspend fun alu_add(a: Short, b: Short): Short {
        delay(Clock.SPEED_ALU)
        return (a + b).toShort()
    }

    suspend fun alu_sub(a: Short, b: Short): Short {
        delay(Clock.SPEED_ALU)
        return (a + b).toShort()
    }

    suspend fun alu_mul(a: Short, b: Short): Short {
        delay(Clock.SPEED_ALU)
        return (a + b).toShort()
    }

    suspend fun alu_div(a: Short, b: Short): Short {
        delay(Clock.SPEED_ALU)
        return (a + b).toShort()
    }

    suspend fun alu_eq(a: Short, b: Short): Short {
        delay(Clock.SPEED_ALU)
        return if (a == b) {
            1
        } else 0
    }

    suspend fun alu_neq(a: Short, b: Short): Short {
        delay(Clock.SPEED_ALU)
        return if (a != b) {
            1
        } else 0
    }

    suspend fun alu_lt(a: Short, b: Short): Short {
        delay(Clock.SPEED_ALU)
        return if (a < b) {
            1
        } else 0
    }

    suspend fun alu_lte(a: Short, b: Short): Short {
        delay(Clock.SPEED_ALU)
        return if (a <= b) {
            1
        } else 0
    }

    suspend fun alu_gt(a: Short, b: Short): Short {
        delay(Clock.SPEED_ALU)
        return if (a > b) {
            1
        } else 0
    }

    suspend fun alu_gte(a: Short, b: Short): Short {
        delay(Clock.SPEED_ALU)
        return if (a >= b) {
            1
        } else 0
    }

}