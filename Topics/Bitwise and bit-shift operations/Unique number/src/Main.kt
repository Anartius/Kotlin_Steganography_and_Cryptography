fun main() {
    val (a, b, c) = readLine()!!.split(" ").map { it.toInt() }
    when (a) {
        a or b -> println(c)
        a or c -> println(b)
        else -> println(a)
    }
}