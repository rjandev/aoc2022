package dev.rjan.aoc2022.day17

import dev.rjan.aoc2022.readInputFile
import dev.rjan.aoc2022.shared.Position

const val DEBUG = false

data class Rock(val number: Int, val currentPositions: MutableSet<Position>) {

    fun move(direction: String = "", blockedPositions: Set<Position> = emptySet()) {
        when (direction) {
            "<" -> move(Int::dec, Int::toInt, blockedPositions)
            ">" -> move(Int::inc, Int::toInt, blockedPositions)
            "" -> move(Int::toInt, Int::dec, blockedPositions)
        }
    }

    fun canMoveDown(blockedPositions: Set<Position>): Boolean {
        val list = currentPositions.map { Position(it.x, it.y - 1) }
            .toMutableList()
        list.addAll(blockedPositions)
        return list.groupingBy { it }.eachCount().filter { it.value > 1 }.isEmpty() && list.all { it.y >= 0 }
    }

    private fun move(xf: (Int) -> Int, yf: (Int) -> Int, blockedPositions: Set<Position>) {
        val newPositions = mutableListOf<Position>()
        for (position in currentPositions) {
            val newX = xf.invoke(position.x)
            val newY = yf.invoke(position.y)
            newPositions.add(Position(newX, newY))
        }
        if (newPositions.any { it.x < 0 || it.x > 6 } || blockedPositions.intersect(newPositions.toSet())
                .isNotEmpty()) {
            return
        }
        currentPositions.clear()
        currentPositions.addAll(newPositions)
    }
}

fun main() {
    print(part1())
    println()
    print(part2())
}

fun part2(): Any {
    TODO("Not yet implemented")
}

fun part1(): Any {
    val moves = readInputFile("day17")
    val rocks = mutableSetOf<Rock>()
    var yMax = -1
    var moveCount = 0
    for (n in (1..2022)) {
        val rock = createRock(n, yMax)
        if (DEBUG) {
            printRocks(rocks, rock)
        }
        val blockedPositions = rocks.flatMap { it.currentPositions }.toSet()

        rock.move(moves[(moveCount % (moves.length))].toString(), blockedPositions)
        moveCount++
        while (rock.canMoveDown(blockedPositions)) {
            rock.move()
            rock.move(moves[(moveCount % (moves.length))].toString(), blockedPositions)
            moveCount++
        }

        rocks.add(rock)
        yMax = rocks.flatMap { it.currentPositions }.toSet().maxOf { it.y }
    }
    return yMax + 1
}

fun printRocks(rocks: MutableSet<Rock>, movingRock: Rock) {
    val positions = rocks.flatMap { it.currentPositions }
    var maxY = positions.maxOfOrNull { it.y }
    maxY = maxOf(maxY ?: 0, movingRock.currentPositions.maxOf { it.y })
    for (y in (0..maxY).reversed()) {
        print('|')
        for (c in 0..6) {
            if (positions.contains(Position(c, y))) {
                print('#')
            } else if (movingRock.currentPositions.contains(Position(c, y))) {
                print('@')
            } else {
                print('.')
            }
        }
        print('|' + System.lineSeparator())
    }
    print("+-------+")
    println()
    println()
}

fun createRock(round: Int, yMax: Int): Rock {
    val shape = round % 5
    val y = yMax + 4
    val positions = when (shape) {
        1 -> listOf(2 to y, 3 to y, 4 to y, 5 to y)
        2 -> listOf(2 to y + 1, 3 to y + 1, 4 to y + 1, 3 to y, 3 to y + 2)
        3 -> listOf(2 to y, 3 to y, 4 to y, 4 to y + 1, 4 to y + 2)
        4 -> listOf(2 to y, 2 to y + 1, 2 to y + 2, 2 to y + 3)
        0 -> listOf(2 to y, 3 to y, 2 to y + 1, 3 to y + 1)
        else -> throw IllegalStateException()
    }.map { Position(it.first, it.second) }

    return Rock(round, positions.toMutableSet())
}


