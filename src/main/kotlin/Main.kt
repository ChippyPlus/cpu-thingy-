package org.cuttlefish

import org.cuttlefish.data.Memory
import org.cuttlefish.data.Register
import org.cuttlefish.pipelining.*
import java.io.File

fun main() {
    var index = 0
    File("main.lx").readLines().forEach { line ->
        if (line.isBlank()) return@forEach
        val encode = Encode(line)
        Memory.write(index.toShort(), encode.full.toShort())
        index++
        if (encode.full2 != null) {
            Memory.write(index.toShort(), encode.full2!!.toShort())
            index++
        }
    }
    // shh! this is encoding

    while (Register.PC.readPrivilege().toInt() < index) {
        IF().fetch()
        ID(pipelined = true).decode()
        EX().execute()
        MEM().memoryWriteBack()
        WB().writeBack()
//        println(execute)
    }
}
