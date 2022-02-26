fun printColor(myImage: BufferedImage) {
    val (x, y) = readLine()!!.split(" ").map { it.toInt() }.toList()
    val rgb = myImage.getRGB(x, y)
    val color = Color(rgb, true)
    println("${color.red} ${color.green} ${color.blue} ${color.alpha}")
}
