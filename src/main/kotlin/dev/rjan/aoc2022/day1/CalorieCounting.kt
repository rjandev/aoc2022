package dev.rjan.aoc2022.day1

import dev.rjan.aoc2022.readInputFile
import kotlin.streams.toList

/**
 * Each input group is separated by two line separators
 */
private val GROUP_DELIMITER = System.lineSeparator() + System.lineSeparator()

fun main() {
    val solution = calculateCalories()

    print(solution)
}

fun calculateCalories() = readInputFile("day1")
    .split(GROUP_DELIMITER)
    .stream()
    .map { it.split(System.lineSeparator()) }
    .map { it.stream().map(String::toLong).reduce(Long::plus) }
    .map { it.orElse(0) }
    .sorted(reverseOrder())
    .toList().take(3).sum()