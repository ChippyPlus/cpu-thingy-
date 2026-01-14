package org.cuttlefish.data

import org.cuttlefish.pipelining.EXResult
import org.cuttlefish.pipelining.ID

object PipeBuffer {
    var pif: List<UShort?>? = null
    var pid: DecodeInstruction? = null
    var pex: EXResult? = null
    var pmem: MEMWruteBackOutput? = null
    var pwb: WriteBackOutput? = null
}