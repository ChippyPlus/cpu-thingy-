package org.cuttlefish.data


import org.cuttlefish.DataFlow
import org.cuttlefish.pipelining.EXResult

object PipeBuffer {
    var p1if: DataFlow = DataFlow()
    var p2id: DataFlow = DataFlow()
    var p3ex: DataFlow = DataFlow()
    var p4mm: DataFlow = DataFlow()
    var p5wb: DataFlow = DataFlow()

    @Deprecated("Using new stages")
    var pif_deprecated: List<UShort?>? = null

    @Deprecated("Using new stages")
    var pid_deprecated: DecodeInstruction? = null

    @Deprecated("Using new stages")
    var pex_deprecated: EXResult? = null

    @Deprecated("Using new stages")
    var pmem_deprecated: MEMWruteBackOutput_old? = null

    @Deprecated("Using new stages")
    var pwb_deprecated: WriteBackOutput_old? = null

    private var instructionCount: Int = 0


    suspend fun step(cycle: Int) {
        println("\n--- Cycle $cycle ---")
        val retired = pwb_deprecated


    }

    override fun toString(): String {
        return "-------------\nFETCH=${pif_deprecated}\nDECODE=$pid_deprecated\nEXECUTE=$pex_deprecated\nMEMORY=$pmem_deprecated\nWRITE=$pwb_deprecated\n-------------\n"
    }
}