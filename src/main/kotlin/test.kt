package org.cuttlefish

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class Pipeline {
    private var stage1: String? = null
    private var stage2: String? = null
    private var stage3: String? = null
    private var stage4: String? = null
    private var stage5: String? = null
    private var instructionCount: Int = 0

    suspend fun step(cycle: Int) {
        println("\n--- Cycle $cycle ---")

        val retired = stage5
        stage5 = stage4
        stage4 = stage3
        stage3 = stage2
        stage2 = stage1

        stage1 = "Instruction $instructionCount"
        instructionCount++

        // 3. Execute Stages
        val results = listOf(
            m1(stage1), m2(stage2), m3(stage3), m4(stage4), m5(stage5)
        )

        // 4. Print Status
        val labels = listOf("S1 (Fetch)", "S2 (Decode)", "S3 (Execute)", "S4 (Memory)", "S5 (Write)")
        labels.zip(results).forEach { (label, msg) ->
            println("| ${label.padEnd(12)} | $msg")
        }

        retired?.let { println("✅ [RETIRED] Completed: $it") }
    }

    private suspend fun m1(s: String?) = delay(10).run { s?.let { "Fetched $it" } ?: "empty" }
    private suspend fun m2(s: String?) = delay(10).run { s?.let { "Decoded ($it)" } ?: "empty" }
    private suspend fun m3(s: String?) = delay(10).run { s?.let { "Executing ($it)" } ?: "empty" }
    private suspend fun m4(s: String?) = delay(10).run { s?.let { "Mem-Stored ($it)" } ?: "empty" }
    private suspend fun m5(s: String?) = delay(10).run { s?.let { "Done ($it)" } ?: "empty" }
}

class Clock(private val maxCycles: Int, private val tickRateMs: Long = 1000) {
    private var currentCycle = 1
    suspend fun tick(block: suspend (Int) -> Unit): Boolean {
        if (currentCycle > maxCycles) return false

        block(currentCycle)
        delay(tickRateMs)
        currentCycle++
        return true
    }
}

suspend fun main() = coroutineScope {
    val pipeline = Pipeline()
    val clock = Clock(maxCycles = 13)

    while (clock.tick { cycle -> pipeline.step(cycle) })

        println("\nSimulation complete.")
}