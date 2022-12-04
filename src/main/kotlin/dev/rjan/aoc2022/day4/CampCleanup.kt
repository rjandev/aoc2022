package dev.rjan.aoc2022.day4

import dev.rjan.aoc2022.readInputFile

fun main() {
    print(part1())
    println()
    print(part2())
}

fun part1(): Int {
    var result = 0
    val groups = readInputFile("day4").split(System.lineSeparator())
    for (group in groups) {
        val split = group.split(",")
        val first = split[0]
        val second = split[1]
        val firstList = parse(first)
        val secondList = parse(second)
        if (firstList.containsAll(secondList) || secondList.containsAll(firstList)) {
            result++
        }
    }
    return result
}

fun parse(item: String): List<Int> {
    val split = item.split("-")
    return (split[0].toInt()..split[1].toInt()).toList()
}

fun part2(): Int {
    var result = 0
    val groups = readInputFile("day4").split(System.lineSeparator())
    for (group in groups) {
        val split = group.split(",")
        val first = split[0]
        val second = split[1]
        val firstList = parse(first)
        val secondList = parse(second)
        if (firstList.any { it in secondList }) {
            result++
        }
    }
    return result
}


