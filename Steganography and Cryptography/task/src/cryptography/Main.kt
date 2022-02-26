package cryptography
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {

    while(true) {
        println("Task (hide, show, exit):")
        when(readLine()!!) {
            "hide" -> hideMessage()
            "show" -> println("Obtaining message from image.")
            "exit" -> {
                println("Bye!")
                return
            }
            else -> println("Wrong task: [input String]")
        }
    }
}

fun hideMessage() {
    println("Input image file:")
    val inputFile = readLine()!!
    println("Output image file:")
    val outputFile = readLine()!!

    val image: BufferedImage
    val input: File
    val output = File(outputFile)

    try {
        input = File(inputFile)
        image = ImageIO.read(input)

    } catch (e: Exception) {
        println("Can't read input file!")
        return
    }

    println("""
        Input image: $inputFile
        Output image: $outputFile
    """.trimIndent())

    val firstBitToOne = 65793
    for (i in 0 until image.width) {
        for (j in 0 until image.height) {
            val rgb = image.getRGB(i, j)
            image.setRGB(i, j, rgb or firstBitToOne)
        }
    }
    ImageIO.write(image, "png", output)
    println("Image $outputFile is saved.")
}

