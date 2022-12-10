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
    if (visited.contains(Pair(x, y))) {
        return
    }
    visited.add(Pair(x, y))
}

fun part2(): Int {
    val input = readInputFile("day9")
    val visited = mutableListOf<Pair<Int, Int>>()
    visit(0, 0, visited)

    val knots = mutableListOf<Pair<Int, Int>>()
    for (knot in 0..9) {
        knots.add(knot, Pair(0, 0))
    }
    val commands = input.lineSequence().toList()
    for (command in commands) {
        val split = command.split(" ")
        val stepCount = split[1].toInt()
        for (n in 0 until stepCount) {
            when (split[0]) {
                "R" -> knots[0] = Pair(knots[0].first + 1, knots[0].second)
                "L" -> knots[0] = Pair(knots[0].first - 1, knots[0].second)
                "U" -> knots[0] = Pair(knots[0].first, knots[0].second + 1)
                "D" -> knots[0] = Pair(knots[0].first, knots[0].second - 1)
            }
            for (x in 1..9) {

                val headX = knots[x - 1].first
                val headY = knots[x - 1].second
                if (isNeighbour(headX, knots[x].first, headY, knots[x].second)) {
                    continue
                }
                if (headX == knots[x].first && headY == knots[x].second) {
                    // HEAD overlaps TAIL FIXME
                    println("head overlaps tail")
                } else if (headX == knots[x].first || headY == knots[x].second) {
                    // only moved on X or Y axis
                    when (split[0]) {
                        "R" -> knots[x] = Pair(knots[x].first + 1, knots[x].second)
                        "L" -> knots[x] = Pair(knots[x].first - 1, knots[x].second)
                        "U" -> knots[x] = Pair(knots[x].first, knots[x].second + 1)
                        "D" -> knots[x] = Pair(knots[x].first, knots[x].second - 1)
                    }
                } else {
                    // diagonal moves
                    when (split[0]) {
                        "R" -> if (headY > knots[x].second) {
                            knots[x] = Pair(knots[x].first + 1, knots[x].second + 1)
                        } else {
                            knots[x] = Pair(knots[x].first + 1, knots[x].second - 1)
                        }

                        "L" -> if (headY > knots[x].second) {
                            knots[x] = Pair(knots[x].first - 1, knots[x].second + 1)
                        } else {
                            knots[x] = Pair(knots[x].first - 1, knots[x].second - 1)
                        }

                        "U" -> if (headX > knots[x].first) {
                            knots[x] = Pair(knots[x].first + 1, knots[x].second + 1)
                        } else {
                            knots[x] = Pair(knots[x].first - 1, knots[x].second + 1)
                        }

                        "D" -> if (headX > knots[x].first) {
                            knots[x] = Pair(knots[x].first + 1, knots[x].second - 1)
                        } else {
                            knots[x] = Pair(knots[x].first - 1, knots[x].second - 1)
                        }
                    }
                }
                if (x == 9) {
                    visit(knots[x].first, knots[x].second, visited)
                }
            }

        }
    }
    return visited.distinct().size
}