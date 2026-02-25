package org.cuttlefish

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.cuttlefish.data.Clock
import org.cuttlefish.data.Memory
import org.cuttlefish.data.PipeBuffer
import org.cuttlefish.data.Register
import org.cuttlefish.pipelining.*
import java.io.File

suspend fun main() = coroutineScope {
    var index = 0
    File("main.lx").readLines().forEach { line ->
        if (line.isBlank()) return@forEach
        val encode = Encode(line)
        Memory.write(index.toShort(), encode.full)
        index++
        if (encode.full2 != null) {
            Memory.write(index.toShort(), encode.full2!!)
            index++
        }
    }

    while (Register.PC.readPrivilege().toInt() < index) {

        Clock.tick { cycle -> cycle(cycle) }

    }


}


suspend fun cycle(cycle: Int) = coroutineScope {


    val ifResult = async { IF().fetch() }
    val idResult = async { ID(PipeBuffer.p1if).decode() }
    val exResult = async { EX(PipeBuffer.p2id).execute() }
    val memResult = async { MEM(PipeBuffer.p3ex).memoryWriteBack() }
    val wbResult = async { WB(PipeBuffer.p4mm).writeBack() }

//    val computeDispatchers = listOf(
//        IF(), ID(PipeBuffer.p1if), EX(PipeBuffer.p2id), MEM(PipeBuffer.p3ex), WB(PipeBuffer.p4mm)
//    )
    val retiredInstruction = PipeBuffer.p4mm

    PipeBuffer.p1if = ifResult.await()
    PipeBuffer.p2id = idResult.await()
    PipeBuffer.p3ex = exResult.await()
    PipeBuffer.p4mm = memResult.await()
    PipeBuffer.p5wb = retiredInstruction
    wbResult.await()
}