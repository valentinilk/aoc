import java.io.File
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

val input = File("input1.txt").readLines()
val times = input[0].split(" ").mapNotNull(String::toDoubleOrNull)
val distances = input[1].split(" ").mapNotNull(String::toDoubleOrNull)
val sum = times.mapIndexed { index, t ->
    val d = distances[index]
    ceil((t + sqrt(t*t-4*d))/2) - floor((t - sqrt(t*t-4*d))/2) - 1
}.reduce(Double::times)
println(sum)
