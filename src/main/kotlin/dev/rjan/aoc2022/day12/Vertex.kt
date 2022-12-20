package dev.rjan.aoc2022.day12

class Vertex(val name: String?) : Comparable<Vertex> {
    // MAX_VALUE assumed to be infinity
    var dist = Int.MAX_VALUE
    var previous: Vertex? = null
    val neighbours: MutableMap<Vertex, Int> = HashMap()
    fun printPath() {
        if (this === previous) {
            System.out.printf("%s", name)
        } else if (previous == null) {
            System.out.printf("%s(unreached)", name)
        } else {
            previous!!.printPath()
            System.out.printf(" -> %s(%d)", name, dist)
        }
    }

    override fun compareTo(other: Vertex): Int {
        return if (dist == other.dist) {
            name!!.compareTo(other.name!!)
        } else Integer.compare(dist, other.dist)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        if (!super.equals(other)) {
            return false
        }
        val vertex = other as Vertex
        if (dist != vertex.dist) {
            return false
        }
        if (if (name != null) name != vertex.name else vertex.name != null) {
            return false
        }
        if (if (previous != null) previous != vertex.previous else vertex.previous != null) {
            return false
        }
        return neighbours == vertex.neighbours
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + dist
        result = 31 * result + if (previous != null) previous.hashCode() else 0
//        result = 31 * result + neighbours.hashCode()
        return result
    }

    override fun toString(): String {
        return "($name, $dist)"
    }
}