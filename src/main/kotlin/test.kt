package org.cuttlefish

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn


suspend fun main() = run {
    while (true) {
        try {
            coroutineScope {
                val ticker = flow {
                    var count = 0
                    while (true) {
                        emit(count++)
                        delay(1000)
                        println(currentCoroutineContext().isActive)
                    }
                }.shareIn(this, SharingStarted.Lazily)

                val jobs = (1..5).map { id ->
                    launch {
                        ticker.collect { tick ->
                            if (tick >= 5) {
                                println("Worker $id is requesting cancel")
                                this@coroutineScope.cancel()
                            }
                            doWork(id, tick)
                        }
                    }

                }
            }
        } catch (e: Exception) {
        }
    }
}

fun doWork(id: Int, tick: Int) {
    println("Worker $id: $tick at ${System.currentTimeMillis() % 10000}ms")
}