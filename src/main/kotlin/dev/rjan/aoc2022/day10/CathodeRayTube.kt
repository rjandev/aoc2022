package dev.rjan.aoc2022.day10

import dev.rjan.aoc2022.readInputFile

fun main() {
    print(part1())
    println()
    print(part2())
}

fun part1(): Int {
    val cycleList = mutableListOf<Int>()
    var x = 1
    var cycle = 0
    var sum = 0
    var instructions = readInputFile("day10").split(System.lineSeparator())
    for (instruction in instructions) {
        if ((cycle == 20 || cycle % 40 == 20) && !cycleList.contains(cycle)) {
            println(cycle)
            cycleList.add(cycle)
            sum += (cycle * x)
        }
        if (instruction.equals("noop")) {
            cycle++
            continue
        }
        val value = instruction.split(" ")
        for (n in 0..1) {
            cycle++
            if ((cycle == 20 || cycle % 40 == 20) && !cycleList.contains(cycle)) {
                println(cycle)
                cycleList.add(cycle)
                sum += (cycle * x)
            }
        }
        x += value[1].toInt()
    }
    return sum
}

fun part2(): List<List<String>> {
    var x = 1
    var cycle = 0
    val instructions = readInputFile("day10").split(System.lineSeparator())
    val screen = mutableListOf<MutableList<String>>()
    for (row in 0..5) {
        screen.add(mutableListOf())
    }

    for (instruction in instructions) {
        for (n in 0..1) {
            val row = cycle / 40
            val pixel = when (x) {
                cycle - (row * 40) -> "#"
                cycle - (row * 40) + 1 -> "#"
                cycle - (row * 40) - 1 -> "#"
                else -> "."
            }
            screen[row].add(pixel)
            cycle++
            if (instruction.equals("noop")) {
                break
            }
        }
        if (instruction.equals("noop")) {
            continue
        }
        val value = instruction.split(" ")
        x += value[1].toInt()
    }
    for (row in 0..5) {
        println(screen[row])
    }
    return screen

}