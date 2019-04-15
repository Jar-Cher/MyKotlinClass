package NoGroupId

import java.lang.StringBuilder
import kotlin.math.*
import java.math.BigInteger
import java.math.BigDecimal



fun main(args: Array<String>) {
    var a = BigDecimal(Math.random() * Math.pow(10.0, 32.0))
    println(a)
    a += BigDecimal(Math.random() * Math.pow(10.0, 16.0))
    println(a)
    a += BigDecimal(Math.random())
    println(a)
    //val a = DecimalFraction.from(-3.67)
    /*println(DecimalFraction.from(20 + PI).integral.toString())
    println(DecimalFraction.from(20 + PI).fraction.toString())
    println(DecimalFraction.from(20 + PI).isNegative.toString())
    println(DecimalFraction.from(-20 + PI).integral.toString())
    println(DecimalFraction.from(-20 + PI).fraction.toString())
    println(DecimalFraction.from(-20 + PI).isNegative.toString())
    println(a.round(3).toString())
    println(a.round(0).toString())
    println(a.round(1).toString())
    println(a.round(2).toString())*/
    //println((-a).toString())
    //println()
}

data class DecimalFraction internal constructor(private var integral: ArrayList<Int>,
                                                private var fraction: ArrayList<Int>,
                                                private var isNegative: Boolean) {

    private var intNumbs: Int = 3
    private var intFract: Int = 3

    init {
        intNumbs = max(intNumbs, integral.size)
        intFract = max(intFract, fraction.size)
        //truncList()
        delZeros()
        if((this.integral.size == 1) && (this.integral[0] == 0) && (this.fraction.size == 0))
            this.isNegative = false
    }

    private fun truncList() {

        integral = ArrayList(integral.filterIndexed { index, _ -> index > (integral.size - intNumbs - 1) })
        fraction = ArrayList(fraction.filterIndexed { index, _ -> index < intFract })

    }

    private fun delZeros() {

        if (fraction.all { it == 0 })
            fraction.clear()

        if (fraction.size > 0)
            while (fraction.last() == 0)
                fraction.removeAt(fraction.size - 1)

        if(integral.size > 1)
            while(integral[0] == 0)
                integral.removeAt(0)
    }

    fun round(digitsAfterDel: Int): DecimalFraction {

        if (digitsAfterDel < 0)
            throw IllegalArgumentException("Incorrect amount of digits given")

        val ans = this.copy()

        if ((digitsAfterDel == 0) && (ans.fraction.size > 0)) {
            if(ans.fraction[0] > 4)
                ans.integral[ans.integral.size - 1]++
            ans.fraction.clear()
        }
        else if (digitsAfterDel < ans.fraction.size) {
            val lNum = ans.fraction[digitsAfterDel]
            if(lNum > 4)
                ans.fraction[digitsAfterDel - 1]++
            ans.fraction = ArrayList(ans.fraction.filterIndexed { index, i ->  index < digitsAfterDel})
        }

        return ans
    }

    companion object {

        val doubleOverflowCheck = from(Double.MAX_VALUE)
        val floatOverflowCheck = from(Float.MAX_VALUE)
        val intOverflowCheck = from(Int.MAX_VALUE)
        val longOverflowCheck = from(Long.MAX_VALUE)

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

            var varInp = inp

            val isNeg = inp.first() == '-'

            // transform scientific notation into somewhat more manageable
            if (varInp.matches(Regex("""^[+-]?[1-9]\.(0|\d*[1-9]+)E-?[1-9]+\d*$"""))) {

                val stPos = if ((varInp.first() == '-') || (varInp.first() == '+')) 1
                else 0

                val ePos = varInp.indexOf('E')

                var absI = varInp[stPos].toString()
                var absR = varInp.substring(stPos + 2, ePos)

                if (varInp[ePos + 1] == '-') {

                    val ord = varInp.substring(ePos + 2).toInt()

                    absR = "0".repeat(ord-1) + absI + absR

                    absI = "0"
                }
                else {

                    val ord = varInp.substring(ePos + 1).toInt()

                    if (ord >= absR.length) {
                        absI += absR
                        absI += "0".repeat(ord - absR.length)
                        absR = ""
                    } else {
                        absI += absR.substring(0, ord)
                        absR = absR.substring(ord)
                    }
                }

                varInp = when {
                    (isNeg) && (absR.isEmpty()) -> "-$absI"
                    (isNeg) && (absR.isNotEmpty()) -> "-$absI.$absR"
                    (!isNeg) && (absR.isNotEmpty()) -> "$absI.$absR"
                    else -> absI
                }

                //println(varInp)

            }

            if (varInp.matches(Regex("""^[+-]?(0|([1-9]\d*))(\.\d+)?$"""))) {

                var curPos = if ((varInp.first() == '-') || (varInp.first() == '+')) 1
                else 0

                val ansI: ArrayList<Int> = arrayListOf()
                val ansR: ArrayList<Int> = arrayListOf()

                if(Regex(""".+\.\d*0+$""").matches(varInp))
                    varInp = varInp.replace(Regex("""\.?0+$"""),"")

                while (curPos != varInp.length) {
                    if (varInp[curPos] != '.') {
                        ansI.add(Character.getNumericValue(varInp[curPos]))
                        curPos++
                    } else
                        break
                }

                if (curPos == varInp.length)
                    return DecimalFraction(ansI, arrayListOf(), isNeg)

                curPos++

                while (curPos != varInp.length) {
                    ansR.add(varInp[curPos].toString().toInt())
                    curPos++
                }

                return DecimalFraction(ansI, ansR, isNeg)
            }

            throw IllegalArgumentException("Incorrect String format")
        }

    }

    fun setDigitsIntegral(inp: Int): DecimalFraction {
        if (inp < 1)
            throw IllegalArgumentException("Incorrect amount of digits given")
        intNumbs = inp
        truncList()
        delZeros()
        return this.copy()
    }

    fun setDigitsFraction(inp: Int): DecimalFraction {
        if (inp < 0)
            throw IllegalArgumentException("Incorrect amount of digits given")
        intFract = inp
        truncList()
        delZeros()
        return this.copy()
    }

    override fun toString(): String {
        val ans = StringBuilder()
        if(isNegative)
            ans.append("-")
        for(i in integral)
            ans.append(i)
        if(fraction.isNotEmpty()) {
            ans.append(".")
            for(i in fraction)
                ans.append(i)
        }

        return ans.toString()
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

    fun toDouble(): Double {
        //val mVal = from("17976931348623157" + "0".repeat(292))
        if(this.abs() > doubleOverflowCheck)
            throw IllegalArgumentException("Double Overflow")
        else
            return this.toString().toDouble()
    }

    fun toFloat(): Float {
        if(this.abs() > floatOverflowCheck)
            throw IllegalArgumentException("Float Overflow")
        else
            return this.toString().toFloat()
    }

    fun toLong(): Long {
        if(this.abs() > longOverflowCheck)
            throw IllegalArgumentException("Long Overflow")
        else
            return this.toString().toLong()
    }

    fun toInt(): Int {
        if(this.abs() > intOverflowCheck)
            throw IllegalArgumentException("Int Overflow")
        else
            return this.toString().toInt()
    }

    operator fun unaryMinus(): DecimalFraction {

        val a = this.copy()
        a.isNegative = !a.isNegative
        return a

    }

    fun abs(): DecimalFraction {
        val ans = this.copy()
        if (this.isNegative)
            ans.isNegative = false
        return ans
    }

    operator fun compareTo(other: DecimalFraction): Int {

        if((other.isNegative) && (!this.isNegative))
            return 1

        if((!other.isNegative) && (this.isNegative))
            return -1

        if(other.integral.size < this.integral.size)
            return if(!this.isNegative)
                1
            else
                -1

        if(other.integral.size > this.integral.size)
            return if(!this.isNegative)
                -1
            else
                1

        for(i in (this.integral.size - 1) downTo 0) {
            if(this.integral[i] > other.integral[i])
                return if(!this.isNegative)
                    1
                else
                    -1
            if(this.integral[i] < other.integral[i])
                return if(!this.isNegative)
                    -1
                else
                    1
        }

        if(other.fraction.size < this.fraction.size)
            return if(!this.isNegative)
                1
            else
                -1

        if(other.fraction.size > this.fraction.size)
            return if(!this.isNegative)
                -1
            else
                1

        for(i in 0 until this.fraction.size) {
            if(this.fraction[i] > other.fraction[i])
                return if(!this.isNegative)
                    1
                else
                    -1
            if(this.fraction[i] < other.fraction[i])
                return if(!this.isNegative)
                    -1
                else
                    1
        }

        return 0
    }

    operator fun plus(other: DecimalFraction): DecimalFraction {

        var ansI: ArrayList<Int> = arrayListOf()
        var ansR: ArrayList<Int> = arrayListOf()

        // summing
        if(!(other.isNegative xor this.isNegative)) {

            var addition = 0

            val stPos = max(this.fraction.size, other.fraction.size) - 1

            for (i in stPos downTo 0) {
                ansR.add((this.fraction.getOrElse(i) { 0 } + other.fraction.getOrElse(i) { 0 } + addition) % 10)
                addition = (this.fraction.getOrElse(i) { 0 } + other.fraction.getOrElse(i) { 0 } + addition) / 10
            }

            ansR = ArrayList(ansR.reversed())

            val endPos = max(this.integral.size, other.integral.size) - 1

            for (i in 0..endPos) {
                ansI.add((this.integral.getOrElse(this.integral.size - i - 1) { 0 }
                        + other.integral.getOrElse(other.integral.size - i - 1) { 0 } + addition) % 10)
                addition = (this.integral.getOrElse(this.integral.size - i - 1) { 0 }
                        + other.integral.getOrElse(other.integral.size - i - 1) { 0 } + addition) / 10
            }

            if(addition > 0)
                ansI.add(addition)

            ansI = ArrayList(ansI.reversed())

            return DecimalFraction(ansI, ansR, this.isNegative)
        }
        // diminution
        else {

            val bigger: DecimalFraction
            val lesser: DecimalFraction

            if(this.abs() > other.abs()) {
                bigger = this.copy()
                lesser = other.copy()
            } else {
                bigger = other.copy()
                lesser = this.copy()
            }

            val stPos = max(this.fraction.size, other.fraction.size) - 1
            var borrowed = false

            for (i in stPos downTo 0){

                var numToAdd = bigger.fraction.getOrElse(i) { 0 } - lesser.fraction.getOrElse(i) { 0 }

                if (borrowed){
                    numToAdd--
                    borrowed = false
                }
                if (numToAdd < 0){
                    borrowed = true
                    numToAdd += 10
                }

                ansR.add(numToAdd)

            }

            ansR = ArrayList(ansR.reversed())

            val endPos = bigger.integral.size - 1

            for (i in 0..endPos) {

                var numToAdd = bigger.integral.getOrElse(bigger.integral.size - i - 1) { 0 } -
                        lesser.integral.getOrElse(lesser.integral.size - i - 1) { 0 }

                if (borrowed){
                    numToAdd--
                    borrowed = false
                }
                if (numToAdd < 0){
                    borrowed = true
                    numToAdd += 10
                }

                ansI.add(numToAdd)

            }

            ansI = ArrayList(ansI.reversed())

            return DecimalFraction(ansI, ansR, bigger.isNegative)
        }
    }

    operator fun minus(other: DecimalFraction): DecimalFraction = this + ( -other)

    operator  fun times(other: DecimalFraction): DecimalFraction {

        val thisOneLined = this.integral + this.fraction
        val otherOneLined = other.integral + other.fraction
        val digitsAfterDot = this.fraction.size + other.fraction.size
        val provAns: ArrayList<ArrayList<Int>> = arrayListOf()
        val extraZeros: ArrayList<Int> = arrayListOf()

        for (i in thisOneLined.size-1 downTo 0) {

            var addition = 0
            val nextLine: ArrayList<Int> = arrayListOf()
            nextLine.addAll(extraZeros)

            for (j in otherOneLined.size - 1 downTo 0) {
                nextLine.add((otherOneLined.getOrElse(j) { 0 } * thisOneLined.getOrElse(i) { 0 } + addition) % 10)
                addition = (otherOneLined.getOrElse(j) { 0 } * thisOneLined.getOrElse(i) { 0 } + addition) / 10
            }

            if(addition > 0)
                nextLine.add(addition)

            extraZeros.add(0)

            provAns.add(nextLine)
        }

        val ansLined: ArrayList<Int> = arrayListOf()
        ansLined.addAll(provAns[0])

        for (i in 1 until provAns.size) {

            var addition = 0

            for (j in 0 until provAns[i].size){
                val nextVal = (ansLined.getOrElse(j) { 0 } + provAns[i].getOrElse(j) { 0 } + addition) % 10
                addition = (ansLined.getOrElse(j) { 0 } + provAns[i].getOrElse(j) { 0 } + addition) / 10
                if(j < ansLined.size)
                    ansLined[j] = nextVal
                else
                    ansLined.add(nextVal)
            }

            if (addition > 0)
                ansLined.add(addition)
        }

        var ansR: ArrayList<Int> = arrayListOf()
        var ansI: ArrayList<Int> = arrayListOf()

        if (digitsAfterDot < ansLined.size) {
            for (i in 0 until digitsAfterDot)
                ansR.add(ansLined[i])
            for (i in digitsAfterDot until ansLined.size)
                ansI.add(ansLined[i])
        } else {
            ansR.addAll(ansLined)
            for (i in ansLined.size..digitsAfterDot)
                ansR.add(0)
            ansI.add(0)
        }

        ansI = ArrayList(ansI.reversed())
        ansR = ArrayList(ansR.reversed())

        return DecimalFraction(ansI, ansR, (this.isNegative xor other.isNegative))
    }
}