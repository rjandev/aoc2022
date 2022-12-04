package dev.rjan.aoc2022.day4

import dev.rjan.aoc2022.readInputFile

fun main() {
    print(part1())
    println()
    print(part2())
}

fun part1(): Long {
    return readInputFile("day4").split(System.lineSeparator())
        .stream().map { parseGroup(it) }
        .filter { it.first.containsAll(it.second) || it.second.containsAll(it.first) }
        .count()
}

fun part2(): Long {
    return readInputFile("day4").split(System.lineSeparator())
        .stream().map { parseGroup(it) }
        .filter { it.first.any { inner -> inner in it.second } }
        .count()
}

private fun parseGroup(group: String): Pair<List<Int>, List<Int>> {
    val split = group.split(",")
    val first = parseAssignment(split[0])
    val second = parseAssignment(split[1])
    return Pair(first, second)
}

fun parseAssignment(item: String): List<Int> {
    val split = item.split("-")
    return (split[0].toInt()..split[1].toInt()).toList()
}


