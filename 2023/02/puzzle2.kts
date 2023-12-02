import java.io.File
import kotlin.math.max
import kotlin.math.min

var sum = 0

val maxRed = 12
val maxGreen = 13
val maxBlue = 14

val cubeRegex = Regex("""(\d+)\s*(\w+)""")

File("input1.txt").forEachLine { line ->
    val splitInput = line.split(":", ";")
    val gameNumber = splitInput[0].removeRange(0..4).toInt()
    var minRed = 0
    var minGreen = 0
    var minBlue = 0
    splitInput.drop(1).forEach { round ->
        cubeRegex.findAll(round).forEach { result ->
            val amount = result.groupValues[1].toInt()
            when (val color = result.groupValues[2]) {
                "red" -> minRed = max(minRed, amount)
                "green" -> minGreen = max(minGreen, amount)
                "blue" -> minBlue = max(minBlue, amount)
                else -> error("Unknown color: $color")
            }
        }
    }
    println("Game $gameNumber requires red: $minRed, green: $minGreen, blue: $minBlue")
    sum += minRed * minGreen * minBlue
}

println("Solution: $sum")
