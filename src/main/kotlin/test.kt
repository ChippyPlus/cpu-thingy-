package org.cuttlefish

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

data class WorkUnit(val tick: Int, val payload: String)

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun main(): Unit = coroutineScope {
    // 1. Create the source of the pipeline (The Ticker)
    val pipelineSource = produce(Dispatchers.Default) {
        var tick = 0
        while (isActive) {
            send(WorkUnit(tick, "Initial Message"))
            println("Source: Sent Tick $tick")
            tick++
            delay(1000) // New work enters every second
        }
    }

    val stage1 = buildStage(this, "Stage 1", pipelineSource, ::m1)
    val stage2 = buildStage(this, "Stage 2", stage1, ::m2)
    val stage3 = buildStage(this, "Stage 3", stage2, ::m3)
    val stage4 = buildStage(this, "Stage 4", stage3, ::m4)
    val resultPipeline = buildStage(this, "Stage 5", stage4, ::m5)

    launch {
        for (result in resultPipeline) {
            println("Result OUT: ${result.payload}")
            println("-----------------------------")
        }
    }
}


@OptIn(ExperimentalCoroutinesApi::class)
fun buildStage(
    scope: CoroutineScope,
    name: String,
    input: ReceiveChannel<WorkUnit>,
    processor: (Int, Int, String) -> String
): ReceiveChannel<WorkUnit> = scope.produce(Dispatchers.Default) {
    for (unit in input) {
        delay(500)

        val newPayload = processor(0, unit.tick, unit.payload)
        println("$name: Processed Tick ${unit.tick}")

        send(unit.copy(payload = newPayload))
    }
}

fun m1(id: Int, tick: Int, msg: String) = "$msg -> [M1]"
fun m2(id: Int, tick: Int, msg: String) = "$msg -> [M2]"
fun m3(id: Int, tick: Int, msg: String) = "$msg -> [M3]"
fun m4(id: Int, tick: Int, msg: String) = "$msg -> [M4]"
fun m5(id: Int, tick: Int, msg: String) = "$msg -> [M5]"