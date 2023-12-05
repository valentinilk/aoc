import java.io.File
import kotlin.math.pow

val input = File("input1.txt").readLines()
val duplicates = IntArray(input.size) { 1 }

input.forEachIndexed { index, line ->
    val splitPrefix = line.split(":")
    val splitSeparator = splitPrefix[1].split("|")
    val winningNumbers = splitSeparator[0].getNumbers()
    val guessedNumbers = splitSeparator[1].getNumbers()
    val guessedCorrect = guessedNumbers.intersect(winningNumbers)
    println("Correct: $guessedCorrect")
    ((index + 1)..index + guessedCorrect.size).forEach { newIndex ->
        duplicates.getOrNull(newIndex)?.let { currentDuplication ->
            duplicates[newIndex] = currentDuplication + duplicates[index]
        }
    }
}

println("Duplicates: ${duplicates.toList()}")
println("Cards in total: ${duplicates.sum()}")

private fun String.getNumbers(): Set<Int> = split(" ")
    .filter(String::isNotEmpty)
    .map(String::toInt)
    .toSet()
