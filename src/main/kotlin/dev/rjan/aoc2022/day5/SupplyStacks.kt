package dev.rjan.aoc2022.day5

import dev.rjan.aoc2022.readInputFile
import kotlin.streams.toList

private const val MOVE_COMMAND_REGEX = "move\\s([0-9]*)\\sfrom\\s([0-9]*)\\sto ([0-9]*)"

fun main() {
    print(part1())
    println()
    print(part2())
}

fun part1(): String {
    return moveCrates(::moveOneAfterAnother)
}

fun part2(): String {
    return moveCrates(::moveAllTogether)
}

private fun moveOneAfterAnother(
    columns: MutableMap<Int, MutableList<String>>,
    from: Int,
    to: Int,
    unused: Long
) {
    val removed = columns[from]?.removeAt(0)
    columns[to]?.add(0, removed!!)
}

private fun moveAllTogether(
    columns: MutableMap<Int, MutableList<String>>,
    from: Int,
    to: Int,
    n: Long
) {
    val removeAt = columns[from]?.removeAt(0)
    columns[to]?.add((n - 1).toInt(), removeAt!!)
}

private fun moveCrates(moveFunction: (MutableMap<Int, MutableList<String>>, Int, Int, Long) -> Unit): String {
    val input = readInputFile("day5").split(System.lineSeparator())
    val stacks = parseStacks(input)
    val columns = transformStackToColumnMap(stacks, initColumnMap(stacks))
    val moveCommands = parseMoveCommands(input)
    moveCommands.forEach { (count, from, to) -> (1..count).forEach { moveFunction(columns, from, to, it) } }

    return columns.values.joinToString("") { it[0] }
}

private fun parseMoveCommands(input: List<String>): List<Triple<Long, Int, Int>> {
    return input.stream()
        .filter { it.startsWith("move") }
        .map { line -> parseMoveCommand(line) }
        .toList()
}

private fun parseMoveCommand(line: String): Triple<Long, Int, Int> {
    val destructured = MOVE_COMMAND_REGEX.toRegex().find(line)?.destructured!!
    val count = destructured.component1().toLong()
    val from = destructured.component2().toInt()
    val to = destructured.component3().toInt()
    return Triple(count, from, to)
}

private fun transformStackToColumnMap(
    stacks: List<String>,
    columns: MutableMap<Int, MutableList<String>>
): MutableMap<Int, MutableList<String>> {
    for (stack in stacks) {
        for (n in 1..columns.size) {
            val offset = (n - 1) * 4
            if (stack.length > offset && stack[offset] == '[') {
                columns[n]?.add(stack[offset + 1].toString())
            }
        }
    }
    return columns
}

private fun initColumnMap(stacks: List<String>): MutableMap<Int, MutableList<String>> {
    val columns = mutableMapOf<Int, MutableList<String>>()
    val columnNames = stacks[stacks.size - 1]
    val last = columnNames[columnNames.length - 1].digitToInt()
    for (n in 1..last) {
        columns[n] = mutableListOf()
    }
    return columns
}

private fun parseStacks(input: List<String>): List<String> {
    return input.stream().takeWhile { "" != it }
        .toList()
}