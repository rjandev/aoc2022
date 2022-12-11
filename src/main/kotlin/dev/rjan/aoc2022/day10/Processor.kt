package dev.rjan.aoc2022.day10

class Processor(
    val instructions: List<Instruction>,
    var cycle: Int = 0,
    val registers: MutableMap<String, Int> = mutableMapOf(),
    val screen: Screen
) {

    init {
        registers["x"] = 1
    }

    var sum = 0
    fun tick() {
        cycle++
        if ((cycle == 20 || cycle % 40 == 20)) {
            println(cycle)
            sum += (cycle * registers["x"]!!)
        }
    }

    fun process() {
        for (instruction in instructions) {
            for (n in 0 until instruction.type.duration) {
                screen.drawPixel(cycle)
                tick()
            }

            if (instruction.type == InstructionType.ADDX) {
                registers["x"] = registers["x"]!! + instruction.args[0].toInt()
                screen.updateSpritePosition(registers["x"]!!)
            }

        }
    }
}