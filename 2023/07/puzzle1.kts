@file:OptIn(ExperimentalStdlibApi::class)

import java.io.File

private data class Hand(val value: String, val bid: Int)

private val cardValue = "23456789TJQKA"
private val hexFormat = HexFormat {
    upperCase = true
    number.removeLeadingZeros = true
}
private var sum = 0

File("input1.txt")
    .readLines()
    .map { line ->
        val split = line.split(" ")
        Hand(
            value = split[0].toHandValue(),
            bid = split[1].toInt(),
        )
    }
    .sortedBy { it.value.hexToInt() }
    .forEachIndexed { index, hand -> sum += (index + 1) * hand.bid }

println("Sum: $sum")

fun String.toHandValue(): String {
    val charCount = HashMap<Char, Int>()
    var value = ""
    forEach {
        charCount[it] = (charCount[it] ?: 0) + 1
        value += cardValue.indexOf(it).toHexString(hexFormat)
    }
    val values = charCount.values.sortedDescending()
    value = ((values.getOrNull(0) ?: 0)).toString() + (values.getOrNull(1) ?: 0).toString() + value
    return value
}
