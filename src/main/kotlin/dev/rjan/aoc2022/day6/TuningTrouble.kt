package dev.rjan.aoc2022.day6

import dev.rjan.aoc2022.readInputFile


fun main() {
    print(part1())
    println()
    print(part2())
}

fun part1(): Int {
    val input = readInputFile("day6")
    return findMarker(input, 4)
}


fun part2(): Int {
    val input = readInputFile("day6")
    return findMarker(input, 14)
}

private fun findMarker(input: String, markerLength: Int): Int {
    val windowed = input.windowed(markerLength)
    return windowed.stream()
        .filter { it.toSet().size == markerLength }
        .map { windowed.indexOf(it) }
        .map { it + markerLength }.findFirst().orElse(-1)
}