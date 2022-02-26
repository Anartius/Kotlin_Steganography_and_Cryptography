fun printARGB() {
    val input = mutableListOf<Int>()
    try {
        input.addAll(
            readLine()!!.split(" ").map { it.toInt() }.toMutableList()
        )
        for (i in input.indices) {
            if (input[i] !in 0..255) throw NumberFormatException()
        }
        if (input.size != 4) throw NumberFormatException()
    } catch (e: NumberFormatException) {
        println("Invalid input")
        return
    }
    val color = Color(input[1], input[2], input[3], input[0])
    println(color.rgb.toUInt())
}