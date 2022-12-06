package dev.rjan.aoc2022.day6

import dev.rjan.aoc2022.readInputFile


fun main() {
    print(part1())
    println()
    print(part2())
}

fun part1(): Int {
    val input = readInputFile("day6")
    for ((index, first) in input.withIndex()) {
        val second = input[index + 1]
        val third = input[index + 2]
        val fourth = input[index + 3]
        if (hashSetOf(first, second, third, fourth).size == 4) {
            return index + 4
        }
    }
    return 0;
}


fun part2(): Int {
    val input = readInputFile("day6")
    for ((index, _) in input.withIndex()) {
        val set = hashSetOf<Char>()
        for (n in 0..13) {
            set.add(input[index + n])
        }
        if (set.size == 14) {
            return index + 14
        }
    }
    return 0;
}