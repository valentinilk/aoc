import java.io.File

var sum = 0

val digitRegex = Regex("""\d+""")
val symbolRegex = Regex("""[^\d\.\n]""")

val input = File("input1.txt").readLines()

input.forEachIndexed { index, line ->
    digitRegex
        .findAll(line)
        .forEach { digitMatch ->
            val digit = digitMatch.value.toInt()
            val lineAbove = maxOf(0, index - 1)
            val lineBeneath = minOf(index + 1, input.size - 1)
            val range = maxOf(digitMatch.range.first - 1, 0)..minOf(digitMatch.range.last + 1, line.length - 1)
            println("Digit found: $digit")
            println("Checking $lineAbove - $lineBeneath in $range")
            if (
                symbolRegex.containsMatchIn(line.substring(range)) ||
                symbolRegex.containsMatchIn(input[lineAbove].substring(range)) ||
                symbolRegex.containsMatchIn(input[lineBeneath].substring(range))
            ) {
                println("Digit found: $digit")
                sum += digit
            }
        }
}

println("Solution: $sum")
