package dev.rjan.aoc2022.day25

enum class SnafuDigits(val stringValue: String, val value: Long) {
    TWO("2", 2),
    ONE("1", 1),
    ZERO("0", 0),
    MINUS("-", -1),
    DOUBLE_MINUS("=", -2);

    companion object {
        fun parse(value: String): SnafuDigits {
            return SnafuDigits.values().find { it.stringValue == value }!!
        }
    }
}
