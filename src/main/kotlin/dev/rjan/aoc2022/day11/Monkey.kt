package dev.rjan.aoc2022.day11

class Monkey(
    private val index: Int,
    private val items: MutableList<Long>,
    private val operation: Operation,
    val test: Test,
    var inspections: Long = 0
) {
    fun hasNoItems(): Boolean = items.isEmpty()
    fun itemCount(): Int = items.size

    fun inspect(reduceWorryLevelFunction: (Long) -> Long) {
        val firstItem = items.removeFirst()
        var newLevel = operation.calculate(firstItem)

        newLevel = reduceWorryLevelFunction.invoke(newLevel)

        items.add(0, newLevel)
        inspections++
    }

    fun throwItem(monkeys: List<Monkey>) {
        val firstItem = items.removeFirst()
        if (firstItem % test.num == 0L) {
            monkeys.find { it.index == test.trueMonkey }!!.items.add(firstItem)
        } else {
            monkeys.find { it.index == test.falseMonkey }!!.items.add(firstItem)
        }
    }
}