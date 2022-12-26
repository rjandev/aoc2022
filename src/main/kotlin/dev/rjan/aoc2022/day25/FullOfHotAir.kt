package dev.rjan.aoc2022.day25

import dev.rjan.aoc2022.readInputFile

fun main() {
    print(part1())
    println()
    print(part2())
}

fun part2(): Any {
    return ""
}

fun part1(): Any {
    val input = readInputFile("day25")
    val lines = input.split(System.lineSeparator())
    val sum = lines.map(::Snafu).sumOf { it.toDecimal() }
    val snafu = Snafu.fromDecimal(sum)
    return snafu.value
}
