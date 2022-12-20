package dev.rjan.aoc2022.day12

import dev.rjan.aoc2022.readInputFile
import dev.rjan.aoc2022.shared.Position


fun main() {
    print(part1())
    println()
    print(part2())
}

fun part1(): Any {
    val input = readInputFile("day12")
    val grid = input.split(System.lineSeparator())

    val (start, end, routes) = parsePaths(input)

    val result = Graph(routes.map { Graph.Edge(it.first.toString(), it.second.toString(), 1) }.toTypedArray())

    result.dijkstra(start.first().toString())
    return result.graph[end.toString()]!!.dist;
}


fun part2(): Any {
    val input = readInputFile("day12")
    val (start, end, routes) = parsePaths(input)
    var shortestPathLength = Int.MAX_VALUE

    for (s in start) {
        val result = Graph(routes.map { Graph.Edge(it.first.toString(), it.second.toString(), 1) }.toTypedArray())
        result.dijkstra(s.toString())
        var dist = result.graph[end.toString()]!!.dist
        if (dist < shortestPathLength) {
            shortestPathLength = dist
        }
    }
    return shortestPathLength
}

fun parsePaths(input: String): Triple<MutableSet<Position>, Position, MutableList<Pair<Position, Position>>> {
    val alternativeStartPositions = mutableSetOf<Position>()
    var startingPosition = Position(-1, -1)
    var targetPosition = Position(-1, -1)
    val split = input.split(System.lineSeparator())
    val result = mutableListOf<Pair<Position, Position>>()
    for ((y, row) in split.withIndex()) {
        for ((x, column) in row.withIndex()) {
            if (column == 'S') {
                startingPosition = Position(x, y)
            }
            if (column == 'a') {
                alternativeStartPositions.add(Position(x, y))
            }
            if (column == 'E') {
                targetPosition = Position(x, y)
            }
            val current = Position(x, y)
            // horizontal
            val currentPosition = readPosition(split, y, x)
            if (x > 0) {
                val left = readPosition(split, y, x - 1)
                if (left <= currentPosition + 1) {
                    val newPosition = Position(x - 1, y)
                    result.add(Pair(current, newPosition))
                }
            }
            if (x < row.length - 1) {
                val right = readPosition(split, y, x + 1)
                if (right <= currentPosition + 1) {
                    val newPosition = Position(x + 1, y)
                    result.add(Pair(current, newPosition))
                }
            }
            // vertical
            if (y > 0) {
                val below = readPosition(split, y - 1, x)
                if (below <= currentPosition + 1) {
                    val newPosition = Position(x, y - 1)
                    result.add(Pair(current, newPosition))
                }
            }
            if (y < split.size - 1) {
                val above = readPosition(split, y + 1, x)
                if (above <= currentPosition + 1) {
                    val newPosition = Position(x, y + 1)
                    result.add(Pair(current, newPosition))
                }
            }
        }
    }


    val startingPositions = mutableSetOf(startingPosition)
    startingPositions.addAll(alternativeStartPositions)
    return Triple(startingPositions, targetPosition, result)
}

private fun readPosition(split: List<String>, y: Int, x: Int): Char {
    return when (val c = split[y][x]) {
        'S' -> 'a'
        'E' -> 'z'
        else -> c
    }
}
