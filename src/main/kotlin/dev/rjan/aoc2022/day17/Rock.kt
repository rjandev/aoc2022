package dev.rjan.aoc2022.day17

import dev.rjan.aoc2022.shared.Position

data class Rock(val currentPositions: MutableSet<Position>) {

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

    companion object {
        fun create(round: Int, yMax: Long): Rock {
            val y = yMax + 4
            val shape = Shape.byId(round % 5)

            return Rock(shape.startingPositions(y.toInt()).toMutableSet())
        }
    }
}