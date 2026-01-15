package org.cuttlefish

import org.cuttlefish.data.Register


data class DataMovementType(
    val name: String,
    val full1: UShort,
    val full2: UShort?,
    val full3: UShort?,
    val format: List<Any>,
    val registerReadLocations: List<Register>?,
    val registerWriteLocations: List<Register>?,
    val memoryReadLocations: List<UShort>?,
    val memoryWriteLocations: List<UShort>?,
)