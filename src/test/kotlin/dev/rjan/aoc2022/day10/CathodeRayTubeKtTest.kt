package dev.rjan.aoc2022.day10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CathodeRayTubeKtTest {

    @Test
    fun day10part1() {
        assertEquals(13140, part1())
    }

    @Test
    fun day10part2() {
        val expected = """
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....
        """.trimIndent().split(System.lineSeparator()).toList()

        assertEquals(expected, part2().map { it.joinToString(separator = "") })
    }
}