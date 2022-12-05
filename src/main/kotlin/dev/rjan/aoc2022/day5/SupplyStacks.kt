package dev.rjan.aoc2022.day5

import dev.rjan.aoc2022.readInputFile

fun main() {
    print(part1())
    println()
    print(part2())
}

fun part1(): String {
    val input = readInputFile("day5").split(System.lineSeparator())
    var stacks = mutableListOf<String>()
    for (line in input) {
        if ("" == line) {
            break
        }
        stacks.add(line)
    }
    var columns = mutableMapOf<Int, MutableList<String>>()
    var columnNames = stacks[stacks.size - 1]
    var last = columnNames[columnNames.length - 1].digitToInt()
    for (n in 1..last) {
        columns[n] = mutableListOf()
    }
    for (stack in stacks) {
        for (n in 1..last) {

            val offset = (n - 1) * 4
            if (stack.length > offset && stack[offset] == '[') {
                columns[n]?.add(stack[offset + 1].toString())
            }
        }
    }
    for (line in input) {
        if (!line.startsWith("move")) {
            continue
        }
        val destructured = "move\\s([0-9]*)\\sfrom\\s([0-9]*)\\sto ([0-9]*)".toRegex().find(line)?.destructured
        val count = destructured?.component1()?.toLong()!!
        val from = destructured?.component2()?.toInt()!!
        val to = destructured?.component3()?.toInt()!!

        for (n in 1..count) {
            val removeAt = columns[from]?.removeAt(0)
            columns[to]?.add(0, removeAt!!)
        }


    }

    var result = ""
    for (n in 1..last) {
        result += columns[n]?.get(0) ?: ""
    }

    return result
}

fun part2(): String {
    val input = readInputFile("day5").split(System.lineSeparator())
    var stacks = mutableListOf<String>()
    for (line in input) {
        if ("" == line) {
            break
        }
        stacks.add(line)
    }
    var columns = mutableMapOf<Int, MutableList<String>>()
    var columnNames = stacks[stacks.size - 1]
    var last = columnNames[columnNames.length - 1].digitToInt()
    for (n in 1..last) {
        columns[n] = mutableListOf()
    }
    for (stack in stacks) {
        for (n in 1..last) {

            val offset = (n - 1) * 4
            if (stack.length > offset && stack[offset] == '[') {
                columns[n]?.add(stack[offset + 1].toString())
            }
        }
    }
    for (line in input) {
        if (!line.startsWith("move")) {
            continue
        }
        val destructured = "move\\s([0-9]*)\\sfrom\\s([0-9]*)\\sto ([0-9]*)".toRegex().find(line)?.destructured
        val count = destructured?.component1()?.toLong()!!
        val from = destructured?.component2()?.toInt()!!
        val to = destructured?.component3()?.toInt()!!

        for (n in 1..count) {
            val removeAt = columns[from]?.removeAt(0)
            val x = (n - 1)
            columns[to]?.add(x.toInt(), removeAt!!)
        }


    }

    var result = ""
    for (n in 1..last) {
        result += columns[n]?.get(0) ?: ""
    }

    return result
}