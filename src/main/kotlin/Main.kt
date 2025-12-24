package org.cuttlefish

import org.cuttlefish.data.Memory
import org.cuttlefish.data.Register
import org.cuttlefish.pipelining.EX
import org.cuttlefish.pipelining.Encode
import org.cuttlefish.pipelining.ID
import org.cuttlefish.pipelining.IF
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
        val instructionFetch: List<UShort?> = IF().fetch()
        val instructionDecode = ID(full1 = instructionFetch[0]!!, full2 = instructionFetch[1])
        val execute = EX(instructionDecode)
        println(execute)
    }
}
