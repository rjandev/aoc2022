package dev.rjan.aoc2022.day10

class Instruction(val type: InstructionType, val args: List<String>) {
    constructor(type: InstructionType, vararg args: String) : this(type, args.toList())
}