import java.io.File

var sum = 0

val digitRegex = Regex("""\d+""")
val gearRegex = Regex("""\*""")

val input = File("input1.txt").readLines()

input.forEachIndexed { index, line ->
    gearRegex
        .findAll(line)
        .forEach { gearMatch ->
            val lineAbove = index - 1
            val lineBeneath = index + 1
            val range = maxOf(gearMatch.range.first - 1, 0)..minOf(gearMatch.range.last + 1, line.length - 1)

            val surroundingDigits = buildList {
                addAll(findDigitsInRange(lineAbove, range))
                addAll(findDigitsInRange(index, range))
                addAll(findDigitsInRange(lineBeneath, range))
            }
            println("Digits found: $surroundingDigits")
            if (surroundingDigits.size == 2) {
                sum += surroundingDigits[0] * surroundingDigits[1]
            }
        }
}

private fun findDigitsInRange(line: Int, range: IntRange): Sequence<Int> {
    return if (line !in input.indices) {
        emptySequence()
    } else {
        digitRegex
            .findAll(input[line])
            .filter { digitMatch -> digitMatch.range.first <= range.last && digitMatch.range.last >= range.first }
            .map { it.value.toInt() }
    }
}

println("Solution: $sum")
