package dev.rjan.aoc2022.day12

import java.util.*


internal class Graph(edges: Array<Edge>) {
    // mapping of vertex names to Vertex objects, built from a set of Edges
    val graph: MutableMap<String, Vertex>

    /**
     * One edge of the graph (only used by Graph constructor)
     */
    class Edge(val v1: String, val v2: String, val dist: Int)

    /**
     * One vertex of the graph, complete with mappings to neighbouring vertices
     */


    /**
     * Builds a graph from a set of edges
     */
    init {
        graph = HashMap(edges.size)

        // one pass to find all vertices
        for (e in edges) {
            if (!graph.containsKey(e.v1)) {
                graph[e.v1] = Vertex(e.v1)
            }
            if (!graph.containsKey(e.v2)) {
                graph[e.v2] = Vertex(e.v2)
            }
        }

        // another pass to set neighbouring vertices
        for (e in edges) {
            graph[e.v1]!!.neighbours[graph[e.v2]!!] = e.dist
            // graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); // also do this for an undirected
            // graph
        }
    }

    /**
     * Runs dijkstra using a specified source vertex
     */
    fun dijkstra(startName: String) {
        if (!graph.containsKey(startName)) {
            System.err.printf(
                "Graph doesn't contain start vertex \"%s\"%n",
                startName
            )
            return
        }
        val source = graph[startName]
        val q: NavigableSet<Vertex?> = TreeSet()

        // set-up vertices
        for (v in graph.values) {
            v.previous = if (v === source) source else null
            v.dist = if (v === source) 0 else Int.MAX_VALUE
            q.add(v)
        }
        dijkstra(q)
    }

    /**
     * Implementation of dijkstra's algorithm using a binary heap.
     */
    private fun dijkstra(q: NavigableSet<Vertex?>) {
        var u: Vertex
        var v: Vertex
        while (!q.isEmpty()) {
            // vertex with shortest distance (first iteration will return source)
            u = q.pollFirst()!!
            if (u.dist == Int.MAX_VALUE) {
                break // we can ignore u (and any other remaining vertices) since they are unreachable
            }
            // look at distances to each neighbour
            for ((key, value) in u.neighbours) {
                v = key // the neighbour in this iteration
                val alternateDist = u.dist + value
                if (alternateDist < v.dist) { // shorter path to neighbour found
                    q.remove(v)
                    v.dist = alternateDist
                    v.previous = u
                    q.add(v)
                }
            }
        }
    }

    /**
     * Prints a path from the source to the specified vertex
     */
    fun printPath(endName: String) {
        if (!graph.containsKey(endName)) {
            System.err.printf(
                "Graph doesn't contain end vertex \"%s\"%n",
                endName
            )
            return
        }
        graph[endName]!!.printPath()
        println()
    }

    /**
     * Prints the path from the source to every vertex (output order is not
     * guaranteed)
     */
    fun printAllPaths() {
        for (v in graph.values) {
            v.printPath()
            println()
        }
    }
}