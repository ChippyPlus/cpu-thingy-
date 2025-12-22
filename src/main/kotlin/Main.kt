package org.cuttlefish

import org.cuttlefish.data.Stack

fun main() {
    Stack.push(1)
    println(Stack.dataStructure.joinToString())
    Stack.push(2)
    println(Stack.dataStructure.joinToString())
    Stack.push(3)
    println(Stack.dataStructure.joinToString())
    Stack.pop()
    println(Stack.dataStructure.joinToString())
    Stack.push(4)
    println(Stack.dataStructure.joinToString())
}