package NoGroupId

import kotlin.math.*

fun main(args: Array<String>) {
    println(DecimalFraction.from(20 + PI).integral.toString())
    println(DecimalFraction.from(20 + PI).fraction.toString())
    println(DecimalFraction.from(20 + PI).isNegative.toString())
    println(DecimalFraction.from(-20 + PI).integral.toString())
    println(DecimalFraction.from(-20 + PI).fraction.toString())
    println(DecimalFraction.from(-20 + PI).isNegative.toString())
}

data class DecimalFraction(var integral: ArrayList<Int>,
                      var fraction: ArrayList<Int>,
                      var isNegative: Boolean) {

    init {
        truncList()
    }

    private fun truncList() {
        integral = ArrayList(integral.filterIndexed { index, i ->  index < intNumbs})
        fraction = ArrayList(fraction.filterIndexed { index, i ->  index < intFract})
    }

    companion object {

        private var intNumbs: Long = 100500
        private var intFract: Long = 100500

        /*
        fun from(inp: Double): DecimalFraction {
            val isNeg = inp < 0
            var intPartInt = abs(inp).toLong()
            var frPartInt = abs(inp) - intPartInt

            var ansI: ArrayList<Int> = arrayListOf()
            while (intPartInt != 0.toLong()) {
                val nextNum = (intPartInt % 10).toInt()
                ansI.add(nextNum)
                intPartInt /= 10
            }

            var ansR: ArrayList<Int> = arrayListOf()
            while (frPartInt != 0.toDouble()) {
                val nextNum = (frPartInt * 10).toInt()
                ansR.add(nextNum)
                frPartInt = (frPartInt * 10) - nextNum
            }

            ansI = ArrayList(ansI.reversed())
            return DecimalFraction(ansI, ansR, isNeg)
        }
        */

        fun from(inp: Double): DecimalFraction = from(inp.toString())

        fun from(inp: Int): DecimalFraction = from(inp.toString())

        fun from(inp: Long): DecimalFraction = from(inp.toString())

        fun from(inp: Float): DecimalFraction = from(inp.toString())

        /*
        fun from(inp: Long): DecimalFraction {
            var intPartInt = inp
            var ansI: ArrayList<Int> = arrayListOf()
            while (intPartInt != 0.toLong()) {
                val nextNum = (intPartInt % 10).toInt()
                ansI.add(nextNum)
                intPartInt /= 10
            }
            return DecimalFraction(ansI, arrayListOf(), inp < 0)
        }
        */

        fun from(inp: String): DecimalFraction {

            if (!inp.matches(Regex("""[+-]?\d+(\.\d+)?""")))
                throw IllegalArgumentException("Incorrect String format")

            val isNeg = inp.first() == '-'
            var curPos = if ((inp.first() == '-') || (inp.first() == '+')) 1
            else 0

            val ansI: ArrayList<Int> = arrayListOf()
            val ansR: ArrayList<Int> = arrayListOf()

            while (curPos != inp.length) {
                if (inp[curPos] != '.') {
                    ansI.add(inp[curPos].toString().toInt())
                    curPos++
                } else
                    break
            }

            if(curPos == inp.length)
                return DecimalFraction(ansI, arrayListOf(), isNeg)

            curPos++

            while (curPos != inp.length) {
                ansR.add(inp[curPos].toString().toInt())
                curPos++
            }

            return DecimalFraction(ansI, ansR, isNeg)
        }

    }


    fun setDigitsIntegral(inp: Long) {
        intNumbs = inp
        truncList()
    }

    fun setDigitsFraction(inp: Long) {
        intFract = inp
        truncList()
    }

    override fun toString(): String {
        var ans = if(isNegative)
            "-" else
            ""
        for(i in integral)
            ans += i
        if(fraction.isNotEmpty()) {
            ans += "."
            for(i in fraction)
                ans += i
        }

        return ans
    }

    /*fun toDouble(): Double {
        var ans = 0.0
        val s = integral.size
        for (i in 0 until s)
            ans += integral[i] * 10.0.pow(s - i - 1)
        for (i in 0 until fraction.size)
            ans += fraction[i] * 10.0.pow(-i-1)
        return if (isNegative)
            -ans
        else
            ans
    }*/

    fun toDouble(): Double = this.toString().toDouble()

    fun toFloat(): Float = this.toString().toFloat()

    fun toLong(): Long = this.toString().toLong()

    fun toInt(): Int = this.toString().toInt()
}