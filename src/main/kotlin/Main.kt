package org.cuttlefish

import org.cuttlefish.data.Memory
import org.cuttlefish.data.Register
import org.cuttlefish.pipelining.Decode
import org.cuttlefish.pipelining.Encode
import java.io.File

fun main() {
    var index = 0
    File("main.lx").readLines().forEach Loop@{ line ->
        if (line.isBlank()) return@Loop
        val encode = Encode(line)
        Memory.write(index.toShort(), encode.full.toShort())
        index++
        if (encode.full2 != null) {
            Memory.write(index.toShort(), encode.full2!!.toShort())
            index++
        }
    }

    while (Register.PC.read().toInt() < index) {
        // fetch????
        Register.INSTR.write(Memory.read(Register.PC.read()))


        println(Register.INSTR.read())
        println(Decode().decode())

        Register.PC.write((Register.PC.read() + 1).toShort())
    }
}
