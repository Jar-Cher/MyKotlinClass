package NoGroupId

import org.junit.Test
import kotlin.test.*

class HelloTest {
    @Test
    fun test1() {
        assertEquals(-3.0, DecimalFraction.from("-3").toDouble())
    }
}
