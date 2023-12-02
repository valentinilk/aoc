import java.io.File

var sum = 0

val maxRed = 12
val maxGreen = 13
val maxBlue = 14

val cubeRegex = Regex("""(\d+)\s*(\w+)""")

File("input1.txt").forEachLine { line ->
    val splitInput = line.split(":", ";")
    val gameNumber = splitInput[0].removeRange(0..4).toInt()
    splitInput.drop(1).forEach { round ->
        cubeRegex.findAll(round).forEach { result ->
            val amount = result.groupValues[1].toInt()
            when (val color = result.groupValues[2]) {
                "red" -> if (amount > maxRed) return@forEachLine
                "green" -> if (amount > maxGreen) return@forEachLine
                "blue" -> if (amount > maxBlue) return@forEachLine
                else -> error("Unknown color: $color")
            }
        }
    }
    println("Game $gameNumber is valid")
    sum += gameNumber
}

println("Solution: $sum")
