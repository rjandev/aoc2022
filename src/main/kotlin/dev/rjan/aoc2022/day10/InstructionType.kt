package dev.rjan.aoc2022.day10

enum class InstructionType(val argumentCount: Int, val duration: Int) {
    NOOP(0, 1),
    ADDX(1, 2)

}