package dev.rjan.aoc2022.day13

import com.google.gson.Gson
import dev.rjan.aoc2022.readInputFile


fun main() {
    print(part1())
    println()
    print(part2())
}


fun part1(): Any {
    val pairs = readInputFile("day13").split(System.lineSeparator() + System.lineSeparator())
        .map { it.split(System.lineSeparator()) }
        .map { it.map { toPackets(it) } }
        .map { Pair(it[0], (it[1])) }

    var inRightOrder = 0

    for ((index, packet) in pairs.withIndex()) {
        val ordered = isOrdered(packet)
        println("${index + 1} is ordered: $ordered")
        if (ordered) {
            inRightOrder += index + 1
        }
    }
    return inRightOrder
}

fun isOrdered(packet: Pair<List<Any>, List<Any>>): Boolean {
    return compareTo(packet) >= 0
}

private fun compareTo(packet: Pair<Any, Any>): Int {
    var a = packet.first
    var b = packet.second

    // gson will parse numbers as double so we first convert them to ints
    if (a is Double) {
        a = a.toInt()
    }
    if (b is Double) {
        b = b.toInt()
    }
    if (a is List<*> && b is Int) {
        b = listOf(b)
    }
    if (a is Int && b is List<*>) {
        a = listOf(a)
    }
    if (a is Int && b is Int) {
        if (a < b) {
            return 1
        }
        if (a == b) {
            return 0
        }
        return -1
    }
    if (a is List<*> && b is List<*>) {
        var i = 0
        while (i < a.size && i < b.size) {
            val res = compareTo(a[i]!! to b[i]!!)
            if (res != 0) {
                return res
            }
            i++
        }
        if (i == a.size) {
            if (a.size == b.size) {
                return 0
            }
            return 1
        }
        return -1
    }
    throw IllegalStateException()
}

fun toPackets(line: String): List<Any> {
    return Gson().fromJson<List<Any>>(line, List::class.java)
}

fun part2(): Any {
    return 0
}