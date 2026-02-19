package org.cuttlefish

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

data class CpuState(
    var tick: Int = 0,
    var s1: String? = null,
    var s2: String? = null,
    var s3: String? = null,
    var s4: String? = null,
    var s5: String? = null
)

suspend fun main() = coroutineScope {
    val cpu = CpuState()
    var cycleCount = 1

    val clock = flow {
        while (cycleCount < 13) {
            emit(Unit)
            delay(1000)
        }
    }

    clock.collect {
        println("\n------- Cycle $cycleCount -------")

        val retired = cpu.s5
        cpu.s5 = cpu.s4
        cpu.s4 = cpu.s3
        cpu.s3 = cpu.s2
        cpu.s2 = cpu.s1

        cpu.s1 = "Instruction ${cpu.tick}"

        val displayS1 = cpu.s1?.let { m1(it) } ?: "empty"
        val displayS2 = cpu.s2?.let { m2(it) } ?: "empty"
        val displayS3 = cpu.s3?.let { m3(it) } ?: "empty"
        val displayS4 = cpu.s4?.let { m4(it) } ?: "empty"
        val displayS5 = cpu.s5?.let { m5(it) } ?: "empty"

        println("| S1 (Fetch)    | $displayS1")
        println("| S2 (Decode)   | $displayS2")
        println("| S3 (Execute)  | $displayS3")
        println("| S4 (Memory)   | $displayS4")
        println("| S5 (Write)    | $displayS5")

        if (retired != null) {
            println("✅ [RETIRED] Finished processing: $retired")
        }

        cpu.tick++
        cycleCount++
    }
}

// Logic functions
fun m1(msg: String) = "Fetched $msg"
fun m2(msg: String) = "Decoded ($msg)"
fun m3(msg: String) = "Executing ($msg)"
fun m4(msg: String) = "Mem-Stored ($msg)"
fun m5(msg: String) = "Done ($msg)"