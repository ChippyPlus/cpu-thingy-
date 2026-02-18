package org.cuttlefish.data


import org.cuttlefish.pipelining.EXResult

object PipeBuffer {
    var pif: List<UShort?>? = null
    var pid: DecodeInstruction? = null
    var pex: EXResult? = null
    var pmem: MEMWruteBackOutput? = null
    var pwb: WriteBackOutput? = null

    override fun toString(): String {
        return "-------------\nFETCH=${pif}\nDECODE=$pid\nEXECUTE=$pex\nMEMORY=$pmem\nWRITE=$pwb\n-------------\n"
    }
}