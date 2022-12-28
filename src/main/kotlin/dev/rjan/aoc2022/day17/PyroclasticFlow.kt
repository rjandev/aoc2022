package dev.rjan.aoc2022.day17

import dev.rjan.aoc2022.readInputFile
import java.util.concurrent.atomic.AtomicInteger

data class State(val shape: Shape, val currentMoveCount: Int, val growth: Int)

fun main() {
    print(part1())
    println()
    print(part2())
}

fun part2(): Any {
    return simulateRocks(1_000_000_000_000L)
}

fun part1(): Any {
    return simulateRocks(2022L)
}

private fun simulateRocks(rockCount: Long): Long {
    val moves = readInputFile("day17")
    val rocks = mutableSetOf<Rock>()
    val moveCount = AtomicInteger(0)

    val (start, end, growth) = analyzeSample()

    for (n in (1 until start)) {
        simulateSingleRock(n, rocks, moves, moveCount)
    }
    val rocksLeft = rockCount - start
    val rocksToCalculate = rocksLeft - (rocksLeft % (end - start))
    val rocksLeftAfterCalc = rocksLeft - rocksToCalculate
    val calculatedHeight = rocksToCalculate / (end - start) * growth

    for (n in start..start + rocksLeftAfterCalc) {
        simulateSingleRock(n.toInt(), rocks, moves, moveCount)
    }

    return rocks.flatMap { it.currentPositions }.maxOf { it.y }.toLong() + calculatedHeight + 1
}

fun analyzeSample(): Triple<Int, Int, Int> {
    val moves = readInputFile("day17")
    val rocks = mutableSetOf<Rock>()
    val moveCount = AtomicInteger(0)

    val states = mutableListOf<State>()
    var n = 1
    while (findLoop(states) == null) {
        val yOld = rocks.flatMap { it.currentPositions }.maxOfOrNull { it.y }?.toLong() ?: 0
        simulateSingleRock(n, rocks, moves, moveCount)
        val yNew = rocks.flatMap { it.currentPositions }.maxOf { it.y }.toLong()
        val yDelta = yNew - yOld
        states.add(State(Shape.byId(n % 5), moveCount.get() % moves.length, yDelta.toInt()))
        n++
    }
    return findLoop(states)!!
}

fun findLoop(list: List<State?>): Triple<Int, Int, Int>? {
    if (list.isEmpty()) {
        return null
    }
    val slowIterator = list.iterator().withIndex()
    val fastIterator = list.iterator().withIndex()
    var slow: IndexedValue<State?>
    var fast: IndexedValue<State?>
    while (fastIterator.hasNext()) {
        slow = slowIterator.next()
        fastIterator.next()
        fast = if (fastIterator.hasNext()) fastIterator.next() else IndexedValue(-1, null)
        if (slow.value == fast.value) {
            return Triple(slow.index, fast.index, list.subList(slow.index, fast.index).sumOf { it!!.growth })
        }
    }
    return null
}

private fun simulateSingleRock(
    n: Int,
    rocks: MutableSet<Rock>,
    moves: String,
    moveCount: AtomicInteger
) {
    val positions = rocks.flatMap { it.currentPositions }.toSet()
    val rock = Rock.create(n, positions.maxOfOrNull { it.y }?.toLong() ?: -1L)

    rock.move(moves[(moveCount.getAndIncrement() % (moves.length))].toString(), positions)
    while (rock.canMoveDown(positions)) {
        rock.move()
        rock.move(moves[(moveCount.getAndIncrement() % (moves.length))].toString(), positions)
    }
    rocks.add(rock)
}


