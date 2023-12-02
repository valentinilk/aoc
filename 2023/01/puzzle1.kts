import java.io.File

var sum = 0

File("input1.txt").forEachLine { line ->
    val firstDigit = line.first { it.isDigit() }
    val lastDigit = line.last { it.isDigit() }
    val combined = "$firstDigit$lastDigit".toInt()
    println("$firstDigit, $lastDigit -> $combined")
    sum += combined
}

println("Solution: $sum")
