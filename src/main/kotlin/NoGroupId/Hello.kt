package NoGroupId

fun main(args: Array<String>) {
    println(DecimalFraction.from("-3").toString())
}

data class DecimalFraction(private val number: Long,
                           private val digitsAfterPoint: Int,
                           private val length: Int) {
    companion object {

        fun from(inp: String): DecimalFraction {

            if (!inp.matches(Regex("""[+-]?\d+(\.\d+)?""")))
                throw IllegalArgumentException("Incorrect String format")

            val commaPos = inp.indexOf('.')
            val isNeg = if ((inp.first() == '-') || (inp.first() == '+')) 1
                        else 0
            val l = inp.length

            if (commaPos == -1)
                return DecimalFraction(inp.toLong(),0, l - isNeg)

            return DecimalFraction(
                inp.replace(".","").toLong(),
                l - commaPos - 1,
                l - isNeg
            )

        }

        fun from(inp: Int): DecimalFraction = from(inp.toString())

        fun from(inp: Long): DecimalFraction = from(inp.toString())

        fun from(inp: Double): DecimalFraction = from(inp.toString())

        fun from(inp: Float): DecimalFraction = from(inp.toString())


    }
}