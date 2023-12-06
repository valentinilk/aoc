import java.io.File

val input = File("input1.txt").readLines()
val mapToRegex = Regex("""(\w+)-to-(\w+)""")

private lateinit var seedMap: List<HashMap<String, UInt>>

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
        return@forEachIndexed
    }
    val input = line.split(" ")
    if (input.size != 3) return@forEachIndexed

    val destinationRangeStart = input[0].toUInt()
    val sourceRangeStart = input[1].toUInt()
    val rangeLength = input[2].toUInt()

    println("Mapping from $mapFrom to $mapTo, range ${sourceRangeStart..<sourceRangeStart + rangeLength}")

    seedMap.forEach { seed ->
        val sourceId = seed[mapFrom] ?: return@forEach
        if (sourceId in sourceRangeStart..<sourceRangeStart + rangeLength) {
            seed[mapTo] = destinationRangeStart + (sourceId - sourceRangeStart)
        }
    }
}
setMissingIds()

seedMap.forEach { seed ->
    println("Seed ${seed["seed"]}, soil ${seed["soil"]}, location ${seed["location"]}")
}
seedMap.sortedBy { seed -> seed["location"] }.first().let { println("Nearest location ${it["location"]}") }


private fun createSeeds(line: String) {
    seedMap = line
        .split(" ")
        .mapNotNull(String::toUIntOrNull)
        .map { id -> hashMapOf("seed" to id) }
}

private fun setMissingIds() {
    if (mapFrom.isNotEmpty()) {
        seedMap
            .filter { seed -> !seed.containsKey(mapTo)}
            .forEach{ seed -> seed[mapTo] = seed[mapFrom]!! }
    }
}
