package dev.rjan.aoc2022.day15

import dev.rjan.aoc2022.readInputFile
import dev.rjan.aoc2022.shared.Position
import kotlin.math.abs


fun main() {
    print(part1(2_000_000))
    println()
    print(part2(0..4_000_000))
}

data class Sensor(val position: Position, val closestBeacon: Position, val dist: Int) {


    companion object {
        fun create(sensor: String): Sensor {
            val regex = "Sensor at x=([-0-9]*), y=([-0-9]*): closest beacon is at x=([-0-9]*), y=([-0-9]*)".toRegex()
            val sensorData = regex.find(sensor)!!.destructured
            val sensorX = sensorData.component1().toInt()
            val sensorY = sensorData.component2().toInt()
            val beaconX = sensorData.component3().toInt()
            val beaconY = sensorData.component4().toInt()
            val sensorPosition = Position(sensorX, sensorY)
            val beaconPosition = Position(beaconX, beaconY)
            val sensorBeaconDist = manhattanDistance(sensorPosition, beaconPosition)
            return Sensor(sensorPosition, beaconPosition, sensorBeaconDist)
        }
    }
}

fun manhattanDistance(a: Position, b: Position) = abs(a.x - b.x) + abs(a.y - b.y);

fun part1(at: Int): Any {
    val sensors = readInputFile("day15").split(System.lineSeparator()).map(Sensor::create)

    val freePositions = mutableSetOf<Position>()

    for (sensor in sensors) {
        determineFreePositions(at, sensor, freePositions)
    }

    return freePositions.size
}

fun part2(intRange: IntRange): Any {
    TODO("Not yet implemented")
}

fun determineFreePositions(atY: Int, sensor: Sensor, freePositions: MutableSet<Position>) {
    val sensorPosition = sensor.position
    val manhattanDistance = manhattanDistance(sensorPosition, sensor.closestBeacon)

    for (x in (sensorPosition.x - manhattanDistance)..(sensorPosition.x + manhattanDistance)) {
        val p = Position(x, atY)
        if (p == sensor.closestBeacon) {
            continue
        }
        if (manhattanDistance(sensorPosition, p) <= manhattanDistance) {
            freePositions.add(p)
        }
    }
}
