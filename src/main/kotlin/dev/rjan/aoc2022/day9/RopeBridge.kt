package dev.rjan.aoc2022.day9

import dev.rjan.aoc2022.readInputFile
import kotlin.math.absoluteValue

fun main() {
    print(part1())
    println()
    print(part2())
}

class Position(var x: Int, var y: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Position

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    companion object {
        fun parse(value: String): Direction = when (value) {
            "U" -> UP
            "D" -> DOWN
            "L" -> LEFT
            "R" -> RIGHT
            else -> throw IllegalArgumentException()
        }
    }
}

class Command(val direction: Direction, val stepCount: Int) {
    constructor(input: String) : this(Direction.parse(input.split(" ")[0]), input.split(" ")[1].toInt())

}

fun part1(): Int {
    val input = readInputFile("day9")
    val knots = mutableListOf<Position>()
    val visited = mutableSetOf<Position>()
    visited.add(Position(0, 0))

    knots.add(Position(0, 0)) // head
    knots.add(Position(0, 0)) // tail

    val commands = input.lineSequence().map(::Command).toList()
    for (command in commands) {
        for (n in 0 until command.stepCount) {
            moveHead(command, knots)
            moveTail(0, 1, knots)
            visited.add(knots[1]) // visit tail position
        }
    }
    return visited.size
}

fun part2(): Int {
    val input = readInputFile("day9")
    val knots = mutableListOf<Position>()
    val visited = mutableSetOf<Position>()
    visited.add(Position(0, 0))

    for (n in 0..9) {
        knots.add(Position(0, 0))
    }

    val commands = input.lineSequence().map(::Command).toList()
    for (command in commands) {
        for (n in 0 until command.stepCount) {
            moveHead(command, knots)
            for ((index, _) in knots.withIndex()) {
                if (index == 0) {
                    continue // this is the head
                }
                moveTail(index - 1, index, knots)
                if (index == 9) {
                    visited.add(knots[index]) // visit tail position
                }
            }
        }
    }
    return visited.size
}

fun moveTail(headIndex: Int, tailIndex: Int, knots: MutableList<Position>) {
    val head = knots[headIndex]
    val tail = knots[tailIndex]
    if (isNeighbour(head.x, tail.x, head.y, tail.y)) {
        return // head and tail are next to each other
    }
    if (head.x == tail.x && head.y == tail.y) {
        return // head and tail overlap
    }
    knots[tailIndex] = moveOneStepTowardsHead(head, tail)
}

fun moveOneStepTowardsHead(head: Position, tail: Position): Position {
    // x-axis only
    if (head.y == tail.y) {
        return if (head.x > tail.x) {
            Position(tail.x + 1, tail.y)
        } else {
            Position(tail.x - 1, tail.y)
        }
    }
    // y-axis only
    if (head.x == tail.x) {
        return if (head.y > tail.y) {
            Position(tail.x, tail.y + 1)
        } else {
            Position(tail.x, tail.y - 1)
        }
    }
    // diagonal moves
    return if (head.x > tail.x) {
        if (head.y > tail.y) {
            Position(tail.x + 1, tail.y + 1)
        } else {
            Position(tail.x + 1, tail.y - 1)
        }
    } else {
        if (head.y > tail.y) {
            Position(tail.x - 1, tail.y + 1)
        } else {
            Position(tail.x - 1, tail.y - 1)
        }
    }
}


private fun isNeighbour(x1: Int, x2: Int, y1: Int, y2: Int) =
    x1.minus(x2).absoluteValue == 1 && y1.minus(y2).absoluteValue == 1 ||
            x1.minus(x2).absoluteValue == 1 && y1 == y2 ||
            y1.minus(y2).absoluteValue == 1 && x1 == x2

private fun moveHead(
    command: Command,
    knots: MutableList<Position>
) {
    when (command.direction) {
        Direction.UP -> knots[0].y++
        Direction.DOWN -> knots[0].y--
        Direction.LEFT -> knots[0].x--
        Direction.RIGHT -> knots[0].x++
    }
}