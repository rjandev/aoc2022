package dev.rjan.aoc2022.day11

class Operation(private val operation: String) {
    fun calculate(item: Long): Long {
        val actualOperation = operation.split("new = ")[1]
        val (_, op, b) = actualOperation.split(" ")

        val y = when (b) {
            "old" -> item
            else -> b.toLong()
        }
        return when (op) {
            "*" -> item * y
            "/" -> item / y
            "+" -> item + y
            "-" -> item - y
            else -> throw UnsupportedOperationException()
        }
    }

}