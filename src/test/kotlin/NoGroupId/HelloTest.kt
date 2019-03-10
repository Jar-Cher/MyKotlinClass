package NoGroupId

import org.junit.Test
import kotlin.test.*

class HelloTest {

    @Test
    fun testCorrectiveness() {
        assertFails { DecimalFraction.from("ololo")}
        assertFails { DecimalFraction.from("09") }
        assertFails { DecimalFraction.from("9.") }
        assertFails { DecimalFraction.from(".990") }
        assertFails { DecimalFraction.from("-") }
        assertFails { DecimalFraction.from("4.4.4") }
        assertFails { DecimalFraction.from("99999999999999999999999999999999999999999").toInt() }
    }

    @Test
    fun testInp() {
        assertEquals(-0.0000001123123123123, DecimalFraction.from(-1.123123123123E-7).toDouble())
        assertEquals(0.001123123123123, DecimalFraction.from(1.123123123123E-3).toDouble())
        assertEquals(-0.0000001123123123123, DecimalFraction.from("-1.123123123123E-7").toDouble())
        assertEquals(DecimalFraction.from(9.9900), DecimalFraction.from("9.9900"))
        assertEquals(DecimalFraction.from(4.0),DecimalFraction.from(4))
        assertEquals(112312312312.3, DecimalFraction.from(112312312.3123E3).toDouble())
        assertEquals(-3.0, DecimalFraction.from(-3).toDouble())
        assertEquals(-3, DecimalFraction.from("-3").toInt())
        assertEquals(-3, DecimalFraction.from("-3").toInt())
        assertEquals(3.627.toFloat(), DecimalFraction.from("3.627").toFloat())
        assertEquals(0, DecimalFraction.from("0").toInt())
        assertEquals(0, DecimalFraction.from(0).toLong())
        assertEquals(0.99999999999999999999999999999999999999999,
            DecimalFraction.from(0.999999999999999999999999999999999999999).toDouble())
        assertEquals(DecimalFraction.from(0.134), DecimalFraction.from(0.1340))
    }

    @Test
    fun testString() {
        assertEquals("-5000", DecimalFraction.from(-5000).toString())
        assertEquals("-4.23", DecimalFraction.from(-4.23).toString())
        assertEquals("4.23", DecimalFraction.from(4.23).toString())
        assertEquals("-4", DecimalFraction.from(-4).toString())
        assertEquals("5", DecimalFraction.from("5").toString())
    }

    @Test
    fun testRound() {
        assertEquals("-4", DecimalFraction.from(-4.23).round(0).toString())
        assertEquals("5", DecimalFraction.from(4.53).round(0).toString())
        assertEquals(-4.7, DecimalFraction.from(-4.678).round(1).toDouble())
        assertEquals("5", DecimalFraction.from("5").round(30).toString())
    }

    @Test
    fun testUnaryMinus() {
        assertEquals("-4", (-DecimalFraction.from(4)).toString())
        assertEquals("4.342", (-DecimalFraction.from("-4.342")).toString())
    }

    @Test
    fun testSetDigitsFraction() {
        assertEquals("-4.345", DecimalFraction.from(-4.3459).setDigitsFraction(3).toString())
        assertEquals("-4.3459", DecimalFraction.from(-4.3459).setDigitsFraction(8).toString())
        assertEquals("4", DecimalFraction.from(4).setDigitsFraction(0).toString())
    }

    @Test
    fun testSetDigitsIntegral() {
        assertEquals("-5.3459", DecimalFraction.from(-345.3459).setDigitsIntegral(1).toString())
        assertEquals("-5674.3459", DecimalFraction.from(-5674.3459).setDigitsIntegral(8).toString())
        assertEquals("4", DecimalFraction.from(4).setDigitsFraction(1).toString())
    }

    @Test
    fun testComparision() {
        assertTrue(DecimalFraction.from(4) > DecimalFraction.from(-5))
        assertTrue(DecimalFraction.from(4.2) > DecimalFraction.from(4))
        assertTrue(DecimalFraction.from(100500) > DecimalFraction.from(9000))
        assertTrue(DecimalFraction.from(746) > DecimalFraction.from(745))
        assertTrue(DecimalFraction.from(0) > DecimalFraction.from(-9556.254))
        assertTrue(DecimalFraction.from(-0.15) > DecimalFraction.from(-0.16))
        assertTrue(DecimalFraction.from(0) < DecimalFraction.from(0.33))
        assertTrue(DecimalFraction.from(0) > DecimalFraction.from(-0.33))
    }

    @Test
    fun testAbs() {
        assertTrue(DecimalFraction.from(0).abs() < DecimalFraction.from(-0.33).abs())
        assertEquals((DecimalFraction.from(4)), (DecimalFraction.from(-4)).abs())
    }

    @Test
    fun testPlusAndMinus() {
        assertEquals(DecimalFraction.from(5000.0) - DecimalFraction.from(-5000),
            DecimalFraction.from("10000"))
        assertEquals(DecimalFraction.from(0) - DecimalFraction.from(0.33),
            DecimalFraction.from(-0.33))
        assertEquals(DecimalFraction.from(1006.4) - DecimalFraction.from(250.4),
            DecimalFraction.from(756))
        assertEquals(DecimalFraction.from(560.24) + DecimalFraction.from(34670.9),
            DecimalFraction.from(35231.14))
        assertEquals(DecimalFraction.from(756) + DecimalFraction.from(250.4),
            DecimalFraction.from(1006.4))
        assertEquals(DecimalFraction.from(4) + DecimalFraction.from(4),
            DecimalFraction.from(8))
        assertEquals(DecimalFraction.from(4) + DecimalFraction.from(-4),
            DecimalFraction.from(0))
        assertEquals(DecimalFraction.from(35231.14) - DecimalFraction.from(34670.9),
            DecimalFraction.from(560.24))
    }

    @Test
    fun testTimes() {
        assertEquals(DecimalFraction.from(-35291.14) * DecimalFraction.from(34670),
            DecimalFraction.from("-1223543823.8"))
        assertEquals(DecimalFraction.from(23) * DecimalFraction.from(45),
            DecimalFraction.from(1035))
        assertEquals(DecimalFraction.from(0) * DecimalFraction.from(0.33),
            DecimalFraction.from(0))
        assertEquals(DecimalFraction.from(5000.0) * DecimalFraction.from(5000),
            DecimalFraction.from("25000000"))
        assertEquals(DecimalFraction.from(-0.1) * DecimalFraction.from(0.33),
            DecimalFraction.from(-0.033))
        assertEquals(DecimalFraction.from(1006.4) * DecimalFraction.from(250.4),
            DecimalFraction.from(252002.56))
        assertEquals(DecimalFraction.from(-560.24) * DecimalFraction.from(-34670.9),
            DecimalFraction.from(19424025.016))
        assertEquals(DecimalFraction.from(756) * DecimalFraction.from(250.4),
            DecimalFraction.from(189302.4))
        assertEquals(DecimalFraction.from(4) * DecimalFraction.from(4),
            DecimalFraction.from(16))
        assertEquals(DecimalFraction.from(4) * DecimalFraction.from(-4),
            DecimalFraction.from(-16))
    }
}
