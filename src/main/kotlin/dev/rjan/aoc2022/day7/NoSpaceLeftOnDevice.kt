package dev.rjan.aoc2022.day7

import dev.rjan.aoc2022.readInputFile
import java.util.*

fun main() {
    print(part1())
    println()
    print(part2())
}

fun part1(): Long {
    val input = readInputFile("day7")
    val lines = input.split(System.lineSeparator())
    val sizes = parseFolders(lines)
    var sum = 0L

    for (size in sizes) {
        if (size.value <= 100_000) {
            sum += size.value
        }
    }
    return sum
}

private fun parseFolders(lines: List<String>): MutableMap<String, Long> {
    val folderSize = mutableMapOf<String, Long>()
    val folderStack = Stack<String>()
    for (line in lines) {
        if (line.startsWith("$")) {
            if (line.startsWith("$ cd")) {
                if (!line.endsWith("..")) {
                    folderStack.push(line.split(" ")[2])
                } else {
                    val currentDirSize = folderSize[toPath(folderStack)]
                    folderStack.pop()
                    folderSize[toPath(folderStack)] =
                        (folderSize[toPath(folderStack)] ?: 0) + (currentDirSize ?: 0)
                }
            }
        } else {
            if (!line.startsWith("dir")) {
                folderSize.putIfAbsent(toPath(folderStack), 0)
                folderSize[toPath(folderStack)] = folderSize[toPath(folderStack)]!! + line.split(" ")[0].toLong()
            }
        }
    }

    while (folderStack.size > 1) {
        val currentDirSize = folderSize[toPath(folderStack)]
        folderStack.pop()
        folderSize[toPath(folderStack)] =
            (folderSize[toPath(folderStack)] ?: 0) + (currentDirSize ?: 0)
    }

    return folderSize
}

private fun toPath(folderStack: Stack<String>) =
    folderStack.joinToString(separator = "/") { it.toString() }.removePrefix("/")


fun part2(): Int {
    val input = readInputFile("day6")
    return 1
}