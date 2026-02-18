package org.cuttlefish

import kotlinx.coroutines.Job
import org.cuttlefish.data.Memory
import org.cuttlefish.data.Register
import org.cuttlefish.pipelining.*
import java.io.File

suspend fun main() {
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

    println(Memory.memory.toList().subList(0,10))
    for (i in 0..6) {
        println(Memory.read(i.toShort()))
    }

    // shh! this is encoding




    while (Register.PC.readPrivilege().toInt() < index) {
        IF().fetch()
//        println(PipeBuffer)
        ID().decode()
//        println(PipeBuffer)
        EX().execute()
//        println(PipeBuffer)
        MEM().memoryWriteBack()
//        println(PipeBuffer)
        WB().writeBack()
//        println(PipeBuffer)

        //        println(execute)
    }
}
