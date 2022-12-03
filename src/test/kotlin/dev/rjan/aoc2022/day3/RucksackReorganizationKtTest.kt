package dev.rjan.aoc2022.day3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RucksackReorganizationKtTest {
    @Test
    fun day3() {
        assertEquals(157, calculateScore())
    }

    @Test
    fun part2() {
        assertEquals(70, calculateGroupScore())
    }
}

