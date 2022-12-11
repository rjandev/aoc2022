package dev.rjan.aoc2022.day9

import dev.rjan.aoc2022.readInputFile
import kotlin.math.absoluteValue

fun main() {
    print(part1())
    println()
    print(part2())
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
    if (isNeighbour(head, tail)) {
        return // head and tail are next to each other
    }
    if (head == tail) {
        return // head and tail overlap
    }
    knots[tailIndex] = moveOneStepTowardsHead(head, tail)
}

fun moveOneStepTowardsHead(head: Position, tail: Position): Position {
    val tailCopy = Position(tail.x, tail.y)
    tailCopy.x += Integer.signum(head.x.compareTo(tail.x))
    tailCopy.y += Integer.signum(head.y.compareTo(tail.y))
    return tailCopy
}

private fun isNeighbour(a: Position, b: Position) =
    a.x.minus(b.x).absoluteValue == 1 && a.y.minus(b.y).absoluteValue == 1 ||
            a.x.minus(b.x).absoluteValue == 1 && a.y == b.y ||
            a.y.minus(b.y).absoluteValue == 1 && a.x == b.x

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