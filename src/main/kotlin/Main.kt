package org.cuttlefish

import kotlinx.coroutines.coroutineScope
import org.cuttlefish.data.Memory
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

    }


}


suspend fun cycle() {
    IF().fetch()
    ID().decode()
    EX().execute()
    MEM().memoryWriteBack()
    WB().writeBack()
}