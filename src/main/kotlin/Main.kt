package org.cuttlefish

import org.cuttlefish.data.Memory
import org.cuttlefish.data.Register
import java.io.File

suspend fun main() {
    var index = 0
    File("main.lx").readLines().forEach { line ->
        if (line.isBlank()) return@forEach
        val encode = Encode(line)
        println("L=$line | ${encode.full}")
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

}
