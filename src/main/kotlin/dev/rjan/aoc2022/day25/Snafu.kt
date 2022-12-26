package dev.rjan.aoc2022.day25

import java.util.*
import kotlin.math.roundToLong

class Snafu(val value: String) {
    fun toDecimal(): Long {
        var result: Long = 0
        val stack = Stack<String>()
        for (c in value) {
            stack.push(c.toString())
        }
        var x = 1L
        while (stack.isNotEmpty()) {
            val numString = stack.pop()
            val snafuDigit = SnafuDigits.parse(numString)
            result += snafuDigit.value.times(x)
            x *= 5L
        }
        return result
    }

    companion object {
        fun fromDecimal(value: Long): Snafu {
            var result = ""
            var temp = value
            while ((temp.toDouble() / 5.0).roundToLong() != 0L) {
                result += temp % 5
                temp = (temp.toDouble() / 5.0).roundToLong()
            }
            result += temp
            result = result.reversed()
            result = result.replace('3', '=')
            result = result.replace('4', '-')
            return Snafu(result)
        }
    }
}
