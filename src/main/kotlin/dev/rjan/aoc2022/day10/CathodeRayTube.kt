package dev.rjan.aoc2022.day10

import dev.rjan.aoc2022.readInputFile

fun main() {
    print(part1())
    println()
    print(part2())
}

fun part1(): Int {
    val instructions = readInputFile("day10").split(System.lineSeparator())
        .map { it.split(" ") }
        .map { Instruction(InstructionType.valueOf(it[0].uppercase()), it.toList().subList(1, it.size)) }

    val processor = Processor(instructions, screen = Screen(40, 6))
    processor.process()
    return processor.sum
}

fun part2(): List<List<String>> {
    val instructions = readInputFile("day10").split(System.lineSeparator())
        .map { it.split(" ") }
        .map { Instruction(InstructionType.valueOf(it[0].uppercase()), it.toList().subList(1, it.size)) }

    val screen = Screen(40, 6)
    val processor = Processor(instructions, screen = screen)
    processor.process()

    screen.render()
    return screen.rows
}