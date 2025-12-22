package org.cuttlefish.data

object Stack {
    var sp: Byte = 0
    val dataStructure = Array<Byte>(32) { 0 }
    fun push(data: Byte) {
        dataStructure[sp.toInt()] = data
        sp = (sp + 1).toByte()
    }

    fun pop(): Byte {
        val dataToUse = dataStructure[sp.toInt()]
        sp = (sp - 1).toByte()
        return dataToUse
    }


}