package NoGroupId

import org.junit.Test
import kotlin.test.*

class HelloTest {
    @Test
    fun test1() {
        assertEquals("-3", DecimalFraction.from("-3").toString())
    }
}
