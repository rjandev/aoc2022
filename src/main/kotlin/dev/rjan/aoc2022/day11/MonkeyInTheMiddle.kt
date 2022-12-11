package dev.rjan.aoc2022.day11

import dev.rjan.aoc2022.readInputFile


const val MONKEY_REGEX =
    "Monkey\\s([0-9]*):\\n\\s\\sStarting\\sitems:\\s([0-9,\\s]*)\\n\\s\\sOperation:\\s([a-z\\s=+\\-*/0-9]*)\\n\\s\\sTest:\\s(\\w*)\\sby\\s([0-9]*)\\n\\s\\s\\s\\sIf true: throw to monkey ([0-9]*)\\n\\s\\s\\s\\sIf false: throw to monkey ([0-9]*)"

fun main() {
    print(part1())
    println()
    print(part2())
}


fun part1(): Any {
    return solve(20) { v, _ -> v / 3 }
}

fun part2(): Any {
    return solve(10_000) { v, monkeys -> v % monkeys.map { it.test.num.toLong() }.reduce(Long::times) }
}

fun parseMonkeys(): List<Monkey> {
    val monkeyList = mutableListOf<Monkey>()
    val regex = MONKEY_REGEX.toRegex()
    val monkeys = readInputFile("day11").split(System.lineSeparator() + System.lineSeparator())
    for (monkeyString in monkeys) {
        val result = regex.find(monkeyString)
        val matchGroup = result!!.groups
        val startingItems = matchGroup[2]!!.value.split(", ").map { it.toLong() }.toMutableList()
        val testNum = matchGroup[5]!!.value.toInt()
        val trueMonkey = matchGroup[6]!!.value.toInt()
        val falseMonkey = matchGroup[7]!!.value.toInt()
        val monkey = Monkey(
            matchGroup[1]!!.value.toInt(),
            startingItems,
            Operation(matchGroup[3]!!.value),
            Test(testNum, trueMonkey, falseMonkey)
        )
        monkeyList.add(monkey)
    }
    return monkeyList
}

private fun solve(numberOfRounds: Int, reduceWorryLevelFunction: (Long, List<Monkey>) -> Long): Long {
    val monkeys = parseMonkeys()

    for (round in 1..numberOfRounds) {
        for (monkey in monkeys) {
            if (monkey.hasNoItems()) {
                continue
            }
            for (n in 0 until monkey.itemCount()) {
                monkey.inspect { value -> reduceWorryLevelFunction(value, monkeys) }
                monkey.throwItem(monkeys)
            }
        }
    }
    val monkeysSortedByMonkeyBusiness = monkeys.sortedBy { it.inspections }.reversed()
    return monkeysSortedByMonkeyBusiness[0].inspections * monkeysSortedByMonkeyBusiness[1].inspections
}
