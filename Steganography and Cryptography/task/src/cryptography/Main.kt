package cryptography

fun main() {
    while(true) {
        println("Task (hide, show, exit):")
        when(readLine()!!) {
            "hide" -> println("Hiding message in image.")
            "show" -> println("Obtaining message from image.")
            "exit" -> {
                println("Bye!")
                return
            }
            else -> println("Wrong task: [input String]")
        }
    }
}

