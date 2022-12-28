package dev.rjan.aoc2022.day17

import dev.rjan.aoc2022.shared.Position

enum class Shape(val id: Int, val startingPositions: (asd: Int) -> Set<Position>) {
    BAR(1, { y ->
        setOf(
            Position(2, y),
            Position(3, y),
            Position(4, y),
            Position(5, y)
        )
    }),
    PLUS(2, {
        setOf(
            Position(2, it + 1),
            Position(3, it + 1),
            Position(4, it + 1),
            Position(3, it),
            Position(3, it + 2)
        )
    }),
    INVERSE_L(3, {
        setOf(
            Position(2, it),
            Position(3, it),
            Position(4, it),
            Position(4, it + 1),
            Position(4, it + 2)
        )
    }),
    STICK(4, {
        setOf(
            Position(2, it),
            Position(2, it + 1),
            Position(2, it + 2),
            Position(2, it + 3)
        )
    }),
    SQUARE(0, {
        setOf(
            Position(2, it),
            Position(3, it),
            Position(2, it + 1),
            Position(3, it + 1)
        )
    });

    companion object {
        fun byId(id: Int): Shape {
            return values().find { it.id == id }!!
        }
    }
}