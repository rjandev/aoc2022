package dev.rjan.aoc2022.day9

class Command(val direction: Direction, val stepCount: Int) {
    constructor(input: String) : this(Direction.parse(input.split(" ")[0]), input.split(" ")[1].toInt())

}