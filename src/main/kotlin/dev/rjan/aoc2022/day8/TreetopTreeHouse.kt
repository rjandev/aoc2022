package dev.rjan.aoc2022.day8

import dev.rjan.aoc2022.readInputFile
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.streams.toList

fun main() {
    print(part1())
    println()
    print(part2())
}

fun part1(): Int {
    val input = readInputFile("day8")
    val grid = input.split(System.lineSeparator())
        .map { it.chunked(1) }
        .map { it.stream().map { inner -> Pair(inner, AtomicBoolean(false)) }.toList() }
        .toList()

    markEdgeTreesVisible(grid)
    calculateScenicScore(grid)

    return grid.stream().flatMap { it.stream() }.filter { it.second.get() }.count().toInt()
}

fun part2(): Int {
    val input = readInputFile("day8")
    val grid = input.split(System.lineSeparator()).map { it.chunked(1) }
        .map { it.stream().map { inner -> Pair(inner, AtomicBoolean(false)) }.toList() }.toList()

    return calculateScenicScore(grid)
}

/**
 * Checks whether the given tree is visible in the given range. The return value is the number of
 * passed trees between the given tree and the next equally big or bigger tree or the edge.
 *
 * @param tree the tree to mark as visible
 * @param valueFunc function to determine the trees around this tree
 * @param intRange the range of trees to check
 * @return number of passed trees
 */
private fun markTreeVisible(
    tree: Pair<String, AtomicBoolean>, valueFunc: (i: Int) -> Pair<String, AtomicBoolean>, intRange: IntProgression
): Int {
    var count = 0
    for (i in intRange) {
        val c = valueFunc.invoke(i)
        count++
        if (c.first.toInt() >= tree.first.toInt()) {
            return count
        }
    }
    tree.second.set(true)
    return count
}

fun markEdgeTreesVisible(grid: List<List<Pair<String, AtomicBoolean>>>) {
    for ((rowIndex, row) in grid.withIndex()) {
        for ((columnIndex, column) in row.withIndex()) {
            if (rowIndex != 0 && rowIndex != grid.size - 1 && columnIndex != 0 && columnIndex != row.size - 1) {
                continue
            }
            column.second.set(true)
        }
    }
}

fun calculateScenicScore(grid: List<List<Pair<String, AtomicBoolean>>>): Int {
    var score = 0
    for ((rowIndex, row) in grid.withIndex()) {
        for ((columnIndex, column) in row.withIndex()) {
            // above
            val topDistance = markTreeVisible(column, { grid[it][columnIndex] }, (0 until rowIndex).reversed())
            val bottomDistance = markTreeVisible(column, { grid[it][columnIndex] }, rowIndex + 1 until grid.size)
            val leftDistance = markTreeVisible(column, { grid[rowIndex][it] }, (0 until columnIndex).reversed())
            val rightDistance = markTreeVisible(column, { grid[rowIndex][it] }, columnIndex + 1 until row.size)

            val newScore = topDistance * bottomDistance * leftDistance * rightDistance
            if (newScore > score) {
                score = newScore
            }
        }

    }
    return score
}
