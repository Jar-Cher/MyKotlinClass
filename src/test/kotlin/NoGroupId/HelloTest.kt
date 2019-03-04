package NoGroupId

import org.junit.Test
import kotlin.test.*

class HelloTest {
    @Test
    fun testInp() {
        assertEquals(-3, DecimalFraction.from("-3").toInt())
        assertEquals(-3.0, DecimalFraction.from(-3).toDouble())
        assertEquals(-3, DecimalFraction.from("-3").toInt())
        assertEquals(3.627.toFloat(), DecimalFraction.from("3.627").toFloat())
        assertEquals(0, DecimalFraction.from("0").toInt())
        assertEquals(0, DecimalFraction.from(0).toLong())
    }

    @Test
    fun testString() {
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
}
