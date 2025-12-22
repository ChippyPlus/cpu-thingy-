package org.cuttlefish.data

object Stack {
    var sp: Short = 0
    val dataStructure = Array<Short>(32) { 0 }
    fun push(data: Short) {
        dataStructure[sp.toInt()] = data
        sp = (sp + 1).toShort()
    }

    fun pop(): Short {
        val dataToUse: Short = dataStructure[sp.toInt()]
        sp = (sp - 1).toShort()
        return dataToUse
    }


}