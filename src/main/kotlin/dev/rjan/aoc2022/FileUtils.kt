package dev.rjan.aoc2022

fun readInputFile(fileName: String): String {
    return {}.javaClass.getResource("/input/$fileName.txt")?.readText()
        ?: throw IllegalArgumentException("Could not read file /input/$fileName.txt")
}