package dev.rjan.aoc2022.day3

import dev.rjan.aoc2022.readInputFile
import kotlin.streams.toList

fun main() {
    print(calculateScore())
    println()
    print(calculateGroupScore())
}

fun calculateScore(): Int {
    val duplicated = mutableListOf<Char>()
    val rucksacks = readInputFile("day3").split(System.lineSeparator())
    for (rucksack in rucksacks) {
        val rucksackList = rucksack.toList()
        val mid = rucksackList.size / 2
        val first = rucksackList.subList(0, mid)
        val second = rucksackList.subList(mid, rucksackList.size)
        duplicated.addAll(first.intersect(second.toSet()))
    }

    return scoreDuplicated(duplicated)
}

fun calculateGroupScore(): Int {
    val duplicated = mutableListOf<Char>()
    val rucksacks = readInputFile("day3").split(System.lineSeparator())
    for ((i, rucksack) in rucksacks.withIndex()) {
        if (i % 3 != 0) {
            continue
        }
        val first = rucksack.toList()
        val second = rucksacks[i + 1].toList()
        val third = rucksacks[i + 2].toList()
        duplicated.addAll(first.intersect(second.toSet()).intersect(third.toSet()))
    }

    return scoreDuplicated(duplicated)
}

fun scoreDuplicated(duplicated: List<Char>): Int {
    return duplicated.stream()
        .map { if (it.isLowerCase()) it.code.minus(96) else it.code.minus(38) }
        .toList().sum()
}
