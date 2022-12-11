package dev.rjan.aoc2022.day11

import dev.rjan.aoc2022.readInputFile


fun main() {
    print(part1())
    println()
    print(part2())
}

class Operation(val operation: String) {
    fun calculate(item: Int): Int {
        var x = 0
        var y = 0
        var new = 0
        val actualOperation = operation.split("new = ")[1]
        val (a, op, b) = actualOperation.split(" ")
        if (a == "old") {
            x = item
        }
        if (b == "old") {
            y = item
        } else {
            y = b.toInt()
        }
        return when (op) {
            "*" -> x * y
            "/" -> x / y
            "+" -> x + y
            "-" -> x - y
            else -> throw UnsupportedOperationException()
        }
    }
}

class Test(val operation: String, val num: Int, val trueMonkey: Int, val falseMonkey: Int)


class Monkey(
    val index: Int,
    val items: MutableList<Int>,
    val operation: Operation,
    val test: Test,
    var inspections: Int = 0
) {
    fun hasItems(): Boolean = items.isNotEmpty()
    fun hasNoItems(): Boolean = items.isEmpty()
    fun itemCount(): Int = items.size

    fun inspect() {
        val firstItem = items.removeFirst()
        var newLevel = operation.calculate(firstItem)
        newLevel /= 3
        items.add(0, newLevel)
        inspections++
    }

    fun throwItem(monkeys: List<Monkey>) {
        val firstItem = items.removeFirst()
        if (firstItem % test.num == 0) {
            monkeys.find { it.index == test.trueMonkey }!!.items.add(firstItem)
        } else {
            monkeys.find { it.index == test.falseMonkey }!!.items.add(firstItem)
        }
    }
}

fun part1(): Any {

    val monkeys = parseMonkeys()

    for (round in 1..20) {
        for (monkey in monkeys) {
            if (monkey.hasNoItems()) {
                continue
            }
            for (n in 0 until monkey.itemCount()) {
                monkey.inspect()
                monkey.throwItem(monkeys)
            }
        }
    }

    var monkeysSortedByMonkeyBusiness = monkeys.sortedBy { it.inspections }.reversed()
    return monkeysSortedByMonkeyBusiness[0].inspections * monkeysSortedByMonkeyBusiness[1].inspections
}

fun parseMonkeys(): List<Monkey> {
    val monkeyList = mutableListOf<Monkey>()
    val regex =
        "Monkey\\s([0-9]*):\\n\\s\\sStarting\\sitems:\\s([0-9,\\s]*)\\n\\s\\sOperation:\\s([a-z\\s=+\\-*/0-9]*)\\n\\s\\sTest:\\s(\\w*)\\sby\\s([0-9]*)\\n\\s\\s\\s\\sIf true: throw to monkey ([0-9]*)\\n\\s\\s\\s\\sIf false: throw to monkey ([0-9]*)".toRegex()
    val monkeys = readInputFile("day11").split(System.lineSeparator() + System.lineSeparator())
    for (monkeyString in monkeys) {
        val result = regex.find(monkeyString)
        val matchGroup = result!!.groups
        val startingItems = matchGroup[2]!!.value.split(", ").map { it.toInt() }.toMutableList()
        val testOperation = matchGroup[4]!!.value
        val testNum = matchGroup[5]!!.value.toInt()
        val trueMonkey = matchGroup[6]!!.value.toInt()
        val falseMonkey = matchGroup[7]!!.value.toInt()
        val monkey = Monkey(
            matchGroup[1]!!.value.toInt(),
            startingItems,
            Operation(matchGroup[3]!!.value),
            Test(testOperation, testNum, trueMonkey, falseMonkey)
        )
        monkeyList.add(monkey)
    }
    return monkeyList
}

fun part2(): Any {
    return 0
}
