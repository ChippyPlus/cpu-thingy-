package org.cuttlefish.data

object Stack {
    const val SIZE = 32
    var sp: Short = 0
    val dataStructure = Array<Short>(SIZE) { 0 }
    fun push(data: Short) {
        dataStructure[(sp % SIZE)] = data
        sp = ((sp + 1) % SIZE).toShort()
    }

    fun pop(): Short {
        sp = ((sp - 1) % SIZE).toShort()
        val dataToUse: Short = dataStructure[(sp % SIZE)]
        return dataToUse
    }


}