package org.cuttlefish

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


suspend fun cycle(cycle: Int) {


    val retired = PipeBuffer.p5wb
    PipeBuffer.p5wb = PipeBuffer.p4mm
    PipeBuffer.p4mm = PipeBuffer.p3ex
    PipeBuffer.p3ex = PipeBuffer.p2id
    PipeBuffer.p2id = PipeBuffer.p1if


    val s1 = IF(PipeBuffer.p1if).fetch()
    val s2 = ID(s1).decode()
    val s3 = EX(s2).execute()
    val s4 = MEM(s3).memoryWriteBack()
    val s5 = WB(s4).writeBack()
}