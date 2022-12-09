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
    val visited = mutableListOf<Pair<Int, Int>>()

    var headX = 0
    var headY = 0
    var tailX = 0
    var tailY = 0
    visit(headX, headY, visited)
    val commands = input.lineSequence().toList()
    for (command in commands) {
        val split = command.split(" ")
        val stepCount = split[1].toInt()
        for (n in 0 until stepCount) {
            when (split[0]) {
                "R" -> headX++
                "L" -> headX--
                "U" -> headY++
                "D" -> headY--
            }
            if (isNeighbour(headX, tailX, headY, tailY)) {
                continue
            }
            if (headX == tailX && headY == tailY) {
                // HEAD overlaps TAIL FIXME
                println("head overlaps tail")
            } else if (headX == tailX || headY == tailY) {
                // only moved on X or Y axis
                when (split[0]) {
                    "R" -> tailX++
                    "L" -> tailX--
                    "U" -> tailY++
                    "D" -> tailY--
                }
            } else {
                // diagonal moves
                when (split[0]) {
                    "R" -> if (headY > tailY) {
                        tailY++; tailX++
                    } else {
                        tailY--; tailX++
                    }

                    "L" -> if (headY > tailY) {
                        tailY++; tailX--
                    } else {
                        tailY--; tailX--
                    }

                    "U" -> if (headX > tailX) {
                        tailX++; tailY++
                    } else {
                        tailX--; tailY++
                    }

                    "D" -> if (headX > tailX) {
                        tailX++; tailY--
                    } else {
                        tailX--; tailY--
                    }
                }
            }
            visit(tailX, tailY, visited)
        }

    }
    return visited.distinct().size
}

private fun isNeighbour(x1: Int, x2: Int, y1: Int, y2: Int) =
    x1.minus(x2).absoluteValue == 1 && y1.minus(y2).absoluteValue == 1 ||
            x1.minus(x2).absoluteValue == 1 && y1 == y2 ||
            y1.minus(y2).absoluteValue == 1 && x1 == x2

private fun visit(
    x: Int,
    y: Int,
    visited: MutableList<Pair<Int, Int>>
) {
    visited.add(Pair(x, y))
}

fun part2(): Int {
    return 0
}