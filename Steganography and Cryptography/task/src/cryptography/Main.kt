package cryptography
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {

    while(true) {
        println("Task (hide, show, exit):")
        when(readLine()!!) {
            "hide" -> hideMessage()
            "show" -> showMessage()
            "exit" -> {
                println("Bye!")
                return
            }
            else -> println("Wrong task: [input String]")
        }
    }
}

fun hideMessage() {
    val endOfMessage = listOf<Byte>(0, 0, 3)
    val inputImage: BufferedImage
    val input: File

    println("Input image file:")
    val inputFile = readLine()!!
    println("Output image file:")
    val outputFile = readLine()!!
    println("Message to hide:")
    val message = readLine()!!.encodeToByteArray().toMutableList()
    message.addAll(endOfMessage)

    val output = File(outputFile)

    try {
        input = File(inputFile)
        inputImage = ImageIO.read(input)
    } catch (e: Exception) {
        println("Can't read input file!")
        return
    }

    val image = BufferedImage(
        inputImage.width,
        inputImage.height,
        BufferedImage.TYPE_INT_RGB
    )
    val graphics = image.createGraphics()
    graphics.drawImage(inputImage, null, 0, 0)
    graphics.dispose()

    if (image.width * image.height < message.size * 8) {
        println("The input image is not large enough to hold this message.")
    }

    encodeMessage(message, image)

    ImageIO.write(image, "png", output)
    println("Message saved in $outputFile image.")
}

fun encodeMessage(message: MutableList<Byte>, image: BufferedImage) {
    var position = 0
    var currentBit = 7
    val one = 1
    var writableBit: Int
    var rgb: Int

    for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            rgb = image.getRGB(x, y)
            writableBit = message[position].toInt() shr(currentBit) shl(31) ushr(31)

            rgb = if (writableBit == 0) {
                rgb and one.inv()
            } else rgb or one
            currentBit--

            if (currentBit < 0) {
                position++
                currentBit = 7
            }

            image.setRGB(x, y, rgb)
            if (position == message.size) return
        }
    }
}

fun showMessage() {

    val input: File
    val image: BufferedImage

    println("Input image file:")
    val inputFile = readLine()!!
    try {
        input = File(inputFile)
        image = ImageIO.read(input)
    } catch (e: Exception) {
        println("Can't read input file!")
        return
    }

    val message = decodeMessage(image)
    println("""
        Message:
        $message
    """.trimIndent())
}

fun decodeMessage(image: BufferedImage): String {
    var rgb: Int
    var lastBit: Int
    var currentByte = 0
    var bitCounter = 7
    val message = mutableListOf<Byte>()


    for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            rgb = image.getRGB(x, y)
            lastBit = rgb shl(31) ushr(31)

            currentByte = currentByte or (lastBit shl bitCounter)
            bitCounter--
            if (bitCounter < 0) {
                message.add(currentByte.toByte())
                currentByte = 0
                bitCounter = 7
            }

            if (checkEndOfMessage(message)) {
                return message.subList(0, message.lastIndex - 3)
                    .toByteArray().toString(Charsets.UTF_8)
            }
        }
    }

    return "oops"
}

fun checkEndOfMessage(message: MutableList<Byte>): Boolean {
    val zero: Byte = 0
    val three: Byte = 3

    return if (message.size > 3) {
        val end = message.subList(message.lastIndex - 3, message.lastIndex)
            .toByteArray()
        end[0] == zero && end[1] == zero && end[2] == three
    } else false
}