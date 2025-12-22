package org.cuttlefish

import java.io.File

fun main() {
    Parser.parse(File("main.lx").readText())
}