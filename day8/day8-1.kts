import java.io.File
import kotlin.math.pow
import kotlin.math.sqrt

val fileName = args[0]
val points = File(fileName).useLines { lines ->
    lines.map { line ->
        val (x, y, z) = line.split(",").map { it.toInt() }
        Point(x, y, z)
    }.toMutableList()
}
val distances = mutableMapOf<Circuit, Double>()
while (points.isNotEmpty()) {
    val pointA = points.first()
    for (pointB in points.drop(1)) {
        distances[Circuit(pointA, pointB)] = pointA.distanceTo(pointB)
    }
    points.removeAt(0)
}

val sortedDistances = distances.entries.sortedBy { it.value }
val circuits = mutableListOf<Circuit>()
sortedDistances.take(1000).forEach { shortestDistance ->
    val matches = circuits.filter { circuit ->
        circuit.joins(shortestDistance.key)
    }
    if (matches.isNotEmpty()) {
        when (matches.size) {
            1 -> {
                matches[0].add(shortestDistance.key)
            }

            2 -> {
                val merged = matches[0]
                merged.add(matches[1])
                circuits.remove(matches[1])
            }

            else -> {
                throw IllegalStateException("More than two matches found!")
            }
        }
    } else {
        circuits.add(shortestDistance.key)
    }
}
println(circuits.sortedByDescending { it.size }.take(3).fold(1) { acc, e -> acc * e.size })

data class Point(val x: Int, val y: Int, val z: Int) {
    fun distanceTo(other: Point): Double {
        // I found the linked Wikipedia page to be far too confusing
        // https://www.calculatorsoup.com/calculators/geometry-solids/distance-two-points.php
        return sqrt(
            (this.x - other.x).toDouble().pow(2.0) +
                (this.y - other.y).toDouble().pow(2.0) +
                (this.z - other.z).toDouble().pow(2.0)
        )
    }
}
data class Circuit(var points: MutableSet<Point>) {
    constructor(first: Point, second: Point) : this(mutableSetOf(first, second))
    fun add(other: Circuit) {
        points.addAll(other.points)
    }
    fun joins(other: Circuit): Boolean {
        return other.points.any { points.contains(it) }
    }
    val size: Int
        get() = this.points.size
    override fun toString(): String {
        return "Circuit(size=$size)"
    }

}
