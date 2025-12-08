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
val iterator = sortedDistances.iterator()
val uniquePoints = distances.keys
    .flatMap{it: Circuit -> listOf(it.points.elementAt(0), it.points.elementAt(1))}
    .distinct().size
var shortestDistance: Circuit = Circuit(Point(0,0,0), Point(0,0,0))
while (iterator.hasNext()) {
    shortestDistance = iterator.next().key
    val matches = circuits.filter { circuit ->
        circuit.joins(shortestDistance)
    }
    if (matches.isNotEmpty()) {
        when (matches.size) {
            1 -> {
                matches[0].add(shortestDistance)
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
        circuits.add(shortestDistance)
    }
    if (circuits.size == 1 && circuits[0].size == uniquePoints) {
        break
    }
}
println(shortestDistance.points.map{it.x}.fold(1){ acc, e -> acc * e })

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
