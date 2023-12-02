import java.io.File

var sum = 0

val digits = setOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

File("input1.txt").forEachLine { line ->
    val firstMatch = line.findAnyOf(digits)!!.second
    val indexOfFirstMatch = digits.indexOf(firstMatch)
    val firstDigit = if (indexOfFirstMatch <= 8) indexOfFirstMatch + 1 else indexOfFirstMatch - 8

    val lastMatch = line.findLastAnyOf(digits)!!.second
    val indexOfLastMatch = digits.indexOf(lastMatch)
    val lastDigit = if (indexOfLastMatch <= 8) indexOfLastMatch + 1 else indexOfLastMatch - 8

    val combined = "$firstDigit$lastDigit".toInt()
    println("$firstDigit (${firstMatch}), $lastDigit (${lastMatch})-> $combined")
    sum += combined
}

println("Solution: $sum")
