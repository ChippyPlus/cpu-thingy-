package org.cuttlefish.pipelining

import org.cuttlefish.Encode
import org.cuttlefish.data.Memory
import org.cuttlefish.data.Register
import org.cuttlefish.data.Stack
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class InstructionTests {

    @BeforeEach
    fun setup() {
        // Reset Registers
        Register.entries.forEach {
            if (it != Register.PC && it != Register.INSTR) {
                it.write(0)
            }
        }
        Register.PC.writePrivilege(0u)
        Register.INSTR.writePrivilege(0u)

        // Reset Memory
        for (i in 0 until Memory.SIZE) {
            Memory.write(i.toShort(), 0)
        }

        // Reset Stack
        Stack.sp = 0
        for (i in Stack.dataStructure.indices) {
            Stack.dataStructure[i] = 0
        }
    }

    private fun execute(instruction: String) {
        val encode = Encode(instruction)
        val pcVal = Register.PC.readPrivilege().toInt()
        Memory.write(pcVal.toShort(), encode.full.toShort())
        if (encode.full2 != null) {
            Memory.write((pcVal + 1).toShort(), encode.full2!!.toShort())
        }

        val ifStage = IF()
        val fetched = ifStage.fetch()
        val idStage = ID(fetched[0]!!, fetched[1])
        val exStage = EX(idStage)
        val memStage = MEM(exStage)
        val wbStage = WB(exStage)
    }

    @Test
    fun testLi() {
        execute("li R1 10")
        assertEquals(10, Register.R1.read())
    }

    @Test
    fun testMov() {
        execute("li R1 10")
        execute("mov R1 R2")
        assertEquals(10, Register.R2.read())
    }

    @Test
    fun testAdd() {
        execute("li R1 10")
        execute("li R2 20")
        execute("add R1 R2 R3")
        assertEquals(30, Register.R3.read())
    }

    @Test
    fun testSub() {
        execute("li R1 30")
        execute("li R2 10")
        execute("sub R1 R2 R3")
        assertEquals(20, Register.R3.read())
    }

    @Test
    fun testMul() {
        execute("li R1 5")
        execute("li R2 6")
        execute("mul R1 R2 R3")
        assertEquals(30, Register.R3.read())
    }

    @Test
    fun testDiv() {
        execute("li R1 20")
        execute("li R2 4")
        execute("div R1 R2 R3")
        assertEquals(5, Register.R3.read())
    }

    @Test
    fun testShl() {
        execute("li R1 1")
        execute("li R2 2")
        execute("shl R1 R2 R3")
        assertEquals(4, Register.R3.read())
    }

    @Test
    fun testShr() {
        execute("li R1 4")
        execute("li R2 1")
        execute("shr R1 R2 R3")
        assertEquals(2, Register.R3.read())
    }

    @Test
    fun testAnd() {
        execute("li R1 5") // 101
        execute("li R2 3") // 011
        execute("and R1 R2 R3") // 001
        assertEquals(1, Register.R3.read())
    }

    @Test
    fun testOr() {
        execute("li R1 5") // 101
        execute("li R2 3") // 011
        execute("or R1 R2 R3") // 111 -> 7
        assertEquals(7, Register.R3.read())
    }

    @Test
    fun testXor() {
        execute("li R1 5") // 101
        execute("li R2 3") // 011
        execute("xor R1 R2 R3") // 110 -> 6
        assertEquals(6, Register.R3.read())
    }

    @Test
    fun testNot() {
        execute("li R1 0")
        execute("not R1 R2")
        assertEquals(-1, Register.R2.read())
    }

    @Test
    fun testEq() {
        execute("li R1 10")
        execute("li R2 10")
        execute("eq R1 R2 R3")
        assertEquals(1, Register.R3.read())

        execute("li R2 11")
        execute("eq R1 R2 R3")
        assertEquals(0, Register.R3.read())
    }

    @Test
    fun testNeq() {
        execute("li R1 10")
        execute("li R2 11")
        execute("neq R1 R2 R3")
        assertEquals(1, Register.R3.read())

        execute("li R2 10")
        execute("neq R1 R2 R3")
        assertEquals(0, Register.R3.read())
    }

    @Test
    fun testLt() {
        execute("li R1 10")
        execute("li R2 11")
        execute("lt R1 R2 R3")
        assertEquals(1, Register.R3.read())

        execute("li R2 10")
        execute("lt R1 R2 R3")
        assertEquals(0, Register.R3.read())
    }

    @Test
    fun testGt() {
        execute("li R1 11")
        execute("li R2 10")
        execute("gt R1 R2 R3")
        assertEquals(1, Register.R3.read())

        execute("li R2 11")
        execute("gt R1 R2 R3")
        assertEquals(0, Register.R3.read())
    }

    @Test
    fun testLte() {
        execute("li R1 10")
        execute("li R2 10")
        execute("lte R1 R2 R3")
        assertEquals(1, Register.R3.read())

        execute("li R2 11")
        execute("lte R1 R2 R3")
        assertEquals(1, Register.R3.read())

        execute("li R2 9")
        execute("lte R1 R2 R3")
        assertEquals(0, Register.R3.read())
    }

    @Test
    fun testGte() {
        execute("li R1 10")
        execute("li R2 10")
        execute("gte R1 R2 R3")
        assertEquals(1, Register.R3.read())

        execute("li R2 9")
        execute("gte R1 R2 R3")
        assertEquals(1, Register.R3.read())

        execute("li R2 11")
        execute("gte R1 R2 R3")
        assertEquals(0, Register.R3.read())
    }

    @Test
    fun testPushPop() {
        execute("li R1 42")
        execute("push R1")
        execute("li R1 0") // Clear R1
        execute("pop R1")
        assertEquals(42, Register.R1.read())
    }

    @Test
    fun testStore() {
        execute("li R1 100") // Address
        execute("li R2 42")  // Value
        execute("st R2 R1")
        assertEquals(42, Memory.read(100))
    }

    @Test
    fun testLoad() {
        // Setup memory manually or via store
        Memory.write(100, 42)
        execute("li R1 100") // Address
        execute("ld R2 R1")
        assertEquals(42, Register.R2.read())
    }

    @Test
    fun testJump() {
        execute("j 10")
        assertEquals(10, Register.PC.readPrivilege().toInt())
    }

    @Test
    fun testJnz() {
        execute("li R1 1")
        execute("jnz R1 10")
        assertEquals(10, Register.PC.readPrivilege().toInt())

        // Reset PC for next part
        Register.PC.writePrivilege(0u)
        execute("li R1 0")
        execute("jnz R1 20")
        // PC should be 2 (li) + 2 (jnz) = 4
        assertEquals(4, Register.PC.readPrivilege().toInt())
    }

    @Test
    fun testJz() {
        execute("li R1 0")
        execute("jz R1 10")
        assertEquals(10, Register.PC.readPrivilege().toInt())

        Register.PC.writePrivilege(0u)
        execute("li R1 1")
        execute("jz R1 20")
        // PC should be 2 (li) + 2 (jz) = 4
        assertEquals(4, Register.PC.readPrivilege().toInt())
    }
    
    @Test
    fun testPr() {
        execute("li R1 123")
        execute("pr R1")
        // Just ensure it doesn't crash
    }
}
