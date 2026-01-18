package org.cuttlefish

import org.cuttlefish.data.Memory
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

    println(Memory.memory.toList().subList(0, 10))
    @Suppress("EmptyRange") for (i in 0..<index) { // it's NOT EMPTY SO SHUT THE FUCK UP!
        println(Memory.read(i.toShort()))
        println(
            message = Decode(full1 = with(Memory) { read(i.toShort()).toUShort() }, full2 = with(Memory) {
                read(
                    ((i.toShort() + (if (i != 10 - 1) 0 else 1).toShort()).toShort())
                ).toUShort()
            }).decode()
        )
    }
// shh! this is encoding

}
