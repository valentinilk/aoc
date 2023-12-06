import java.io.File
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

val input = File("input1.txt").readLines()
val t = input[0].split(":")[1].replace(" ", "").toDouble()
val d = input[1].split(":")[1].replace(" ", "").toDouble()
val options = ceil((t + sqrt(t*t-4*d))/2) - floor((t - sqrt(t*t-4*d))/2) - 1
println(options)
