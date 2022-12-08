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
    val edgeCount = countEdgeTrees(grid)
    val otherTrees = countTrees(grid)

    for (pairs in grid) {
        println(pairs.stream().map { it.first + if (it.second.get()) "T" else "F" }.toList())
    }

    val visibleTrees = grid.stream().flatMap { it.stream() }.filter { it.second.get() }.count().toInt()
    val countedVisibleTrees = edgeCount + otherTrees
    return visibleTrees
}

fun countTrees(grid: List<List<Pair<String, AtomicBoolean>>>): Int {
    var treeCount = 0
    for ((rowIndex, row) in grid.withIndex()) {
        if (rowIndex == 0 || rowIndex == grid.size - 1) {
            continue
        }
        for ((columnIndex, column) in row.withIndex()) {
            if (columnIndex == 0 || columnIndex == row.size - 1) {
                continue
            }
            // above
            val above = grid[rowIndex - 1][columnIndex]
            if (false && above.second.get()) {
                if (above.first.toInt() < column.first.toInt()) {
//                    column.second.set(true)
                    treeCount++
                    continue
                }
            } else {
                var isHidden = false
                for (i in 0 until rowIndex) {
                    val c = grid[i][columnIndex]
                    if (c.first.toInt() >= column.first.toInt()) {
                        isHidden = true
                        break
                    }
                }
                if (!isHidden) {
                    treeCount++
                    column.second.set(true)
                }
            }
            // below
            val below = grid[rowIndex + 1][columnIndex]
            if (false && below.second.get()) {
                if (below.first.toInt() < column.first.toInt()) {
//                    column.second.set(true)
                    treeCount++
                    continue
                }
            } else {
                var isHidden = false
                for (i in rowIndex + 1 until grid.size) {
                    val c = grid[i][columnIndex]
                    if (c.first.toInt() >= column.first.toInt()) {
                        isHidden = true
                        break
                    }
                }
                if (!isHidden) {
                    treeCount++
                    column.second.set(true)
                }

            }
            // left
            val left = grid[rowIndex][columnIndex - 1]
            if (false && left.second.get()) {
                if (left.first.toInt() < column.first.toInt()) {
//                    column.second.set(true)
                    treeCount++
                    continue
                }
            } else {
                var isHidden = false
                for (i in 0 until columnIndex) {
                    val c = grid[rowIndex][i]
                    if (c.first.toInt() >= column.first.toInt()) {
                        isHidden = true
                        break
                    }
                }
                if (!isHidden) {
                    treeCount++
                    column.second.set(true)
                }
            }
            // right
            val right = grid[rowIndex][columnIndex + 1]
            if (false && right.second.get()) {
                if (right.first.toInt() < column.first.toInt()) {
//                    column.second.set(true)
                    treeCount++
                    continue
                }
            } else {
                var isHidden = false
                for (i in columnIndex + 1 until row.size) {
                    val c = grid[rowIndex][i]
                    if (c.first.toInt() >= column.first.toInt()) {
                        isHidden = true
                        break
                    }
                }
                if (!isHidden) {
                    treeCount++
                    column.second.set(true)
                }
            }
        }
    }
    return treeCount
}

fun countEdgeTrees(grid: List<List<Pair<String, AtomicBoolean>>>): Int {
    var top = 0
    var bottom = 0
    var left = 0
    var right = 0
    for (column in grid[0]) {
        top++
        column.second.set(true)
    }
    for (column in grid[grid.size - 1]) {
        bottom++
        column.second.set(true)
    }
    for ((rowIndex, row) in grid.withIndex()) {
        for ((columnIndex, column) in row.withIndex()) {
            if (rowIndex == 0 || rowIndex == grid.size - 1 || (columnIndex != 0 && columnIndex != row.size - 1)) {
                continue
            }
            if (columnIndex == 0) {
                left++
                column.second.set(true)
            }
            if (columnIndex == row.size - 1) {
                right++
                column.second.set(true)
            }
        }
    }
    return top + bottom + left + right
}

fun part2(): Int {
    val input = readInputFile("day8")
    val grid = input.split(System.lineSeparator())
        .map { it.chunked(1) }
        .map { it.stream().map { inner -> Pair(inner, AtomicBoolean(false)) }.toList() }
        .toList()
    val treeScore = calculateScenticScore(grid)


    return treeScore
}

fun calculateScenticScore(grid: List<List<Pair<String, AtomicBoolean>>>): Int {
    var score = 0

    for ((rowIndex, row) in grid.withIndex()) {
        for ((columnIndex, column) in row.withIndex()) {
            var topDistance = 0
            var bottomDistance = 0
            var leftDistance = 0
            var rightDistance = 0
            // above
            for (i in (0 until rowIndex).reversed()) {
                val c = grid[i][columnIndex]
                topDistance = rowIndex - i
                if (c.first.toInt() >= column.first.toInt()) {
                    break
                }
            }
            // below
            for (i in rowIndex + 1 until grid.size) {
                val c = grid[i][columnIndex]
                bottomDistance = i - rowIndex
                if (c.first.toInt() >= column.first.toInt()) {
                    break
                }
            }

            // left
            for (i in (0 until columnIndex).reversed()) {
                val c = grid[rowIndex][i]
                leftDistance = columnIndex - i
                if (c.first.toInt() >= column.first.toInt()) {
                    break
                }
            }
            // right
            for (i in columnIndex + 1 until row.size) {
                val c = grid[rowIndex][i]
                rightDistance = i - columnIndex
                if (c.first.toInt() >= column.first.toInt()) {
                    break
                }
            }
            val newScore = topDistance * bottomDistance * leftDistance * rightDistance
            if (newScore > score) {
                score = newScore
            }
        }

    }
    return score
}
