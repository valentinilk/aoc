import org.w3c.dom.ranges.Range
import java.io.File
import java.util.Queue

val input = File("input1.txt").readLines()
val mapToRegex = Regex("""(\w+)-to-(\w+)""")

private lateinit var idMap: HashMap<String, MutableList<Pair<UIntRange, UIntRange>>>
private var unhandledIds: MutableList<UIntRange> = mutableListOf()

var mapFrom = ""
var mapTo = ""
input.forEachIndexed { index, line ->
    if (index == 0) {
        createSeeds(line)
        return@forEachIndexed
    }
    if (mapToRegex.containsMatchIn(line)) {
        setMissingIds()
        val match = mapToRegex.find(line)!!
        mapFrom = match.groupValues[1]
        mapTo = match.groupValues[2]
        idMap[mapTo] = mutableListOf()
        unhandledIds = idMap[mapFrom]!!.map { it.second }.toMutableList()
        return@forEachIndexed
    }
    val input = line.split(" ")
    if (input.size != 3) return@forEachIndexed

    val destinationRangeStart = input[0].toUInt()
    val sourceRangeStart = input[1].toUInt()
    val rangeLength = input[2].toUInt()

    println("Mapping from $mapFrom to $mapTo")
    val newUnhandled: MutableList<UIntRange> = mutableListOf()
    unhandledIds.forEach { idRange ->
        newUnhandled.addAll(modifyRange(idRange, sourceRangeStart, destinationRangeStart, rangeLength))
    }
    println("New unhandled: $newUnhandled")
    unhandledIds = newUnhandled
    println("${idMap[mapTo]}")
}
setMissingIds()

idMap["location"]!!
    .minOf { it.second.first }
    .let { println("Closest location: $it") }
println("Length: ${idMap["location"]!!.size}")

private fun createSeeds(line: String) {
    val seeds = line
        .removePrefix("seeds: ")
        .split(" ")
        .mapNotNull(String::toUIntOrNull)
        .chunked(2)
        .map { pairs ->
            0u..1u to pairs[0]..(pairs[1] + pairs[0])
        }
        .toMutableList()
    idMap = hashMapOf("seed" to seeds)
}

private fun setMissingIds() {
    if (mapFrom.isNotEmpty()) {
        unhandledIds.forEach { idRange -> idMap[mapTo]!!.add(idRange to idRange) }
    }
}

private fun modifyRange(
    subject: UIntRange,
    source: UInt,
    destination: UInt,
    range: UInt,
): List<UIntRange> {
    val sourceRange = source..<source+range
    val intersects = subject.intersects(sourceRange)
    println("Checking: $subject intersects $sourceRange to ${destination..<destination+range}: $intersects")
    return if (intersects) {
        val intersect = subject.intersect(sourceRange)
        val offset = destination - source
        val newRange = intersect.first + offset..intersect.last + offset
        idMap[mapTo]!!.add(subject to newRange)
        subject - sourceRange
    } else {
        listOf(subject)
    }
}

private fun UIntRange.intersect(other: UIntRange): UIntRange {
    return if (!this.intersects(other)) {
        UIntRange.EMPTY
    } else {
        kotlin.math.max(this.first, other.first)..kotlin.math.min(this.last, other.last)
    }
}

private operator fun UIntRange.minus(other: UIntRange): List<UIntRange> {
    return if (!this.intersects(other)) {
        listOf(this)
    } else {
        val left = if (first < other.first) first..<other.first else null
        val right = if (last > other.last) (other.last+1u)..last else null
        listOfNotNull(left, right)
    }
}

private fun UIntRange.intersects(other: UIntRange) = first <= other.last && last >= other.first
