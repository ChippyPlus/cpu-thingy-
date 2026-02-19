package org.cuttlefish

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

object Buffer {
    var stage1: String? = null
    var stage2: String? = null
    var stage3: String? = null
    var stage4: String? = null
    var stage5: String? = null
    var tick: Int = 0
}

suspend fun main() = coroutineScope {
    var cycleCount = 1

    val clock = flow {
        while (cycleCount <= 13) {
            emit(Unit)
            delay(1000)
        }
    }

    clock.collect {
        println("\n--- Cycle $cycleCount ---")

        val retired = Buffer.stage5
        Buffer.stage5 = Buffer.stage4
        Buffer.stage4 = Buffer.stage3
        Buffer.stage3 = Buffer.stage2
        Buffer.stage2 = Buffer.stage1

        Buffer.stage1 = "Instruction ${Buffer.tick}"
        Buffer.tick++

        val s1Msg = m1()
        val s2Msg = m2()
        val s3Msg = m3()
        val s4Msg = m4()
        val s5Msg = m5()

        println("| S1 (Fetch)   | $s1Msg")
        println("| S2 (Decode)  | $s2Msg")
        println("| S3 (Execute) | $s3Msg")
        println("| S4 (Memory)  | $s4Msg")
        println("| S5 (Write)   | $s5Msg")

        if (retired != null) {
            println("✅ [RETIRED] Completed: $retired")
        }

        cycleCount++
    }
}

suspend fun m1(): String {
    delay(10)
    return Buffer.stage1?.let { "Fetched $it" } ?: "empty"
}

suspend fun m2(): String {
    delay(10)
    return Buffer.stage2?.let { "Decoded ($it)" } ?: "empty"
}

suspend fun m3(): String {
    delay(10)
    return Buffer.stage3?.let { "Executing ($it)" } ?: "empty"
}

suspend fun m4(): String {
    delay(10)
    return Buffer.stage4?.let { "Mem-Stored ($it)" } ?: "empty"
}

suspend fun m5(): String {
    delay(10)
    return Buffer.stage5?.let { "Done ($it)" } ?: "empty"
}