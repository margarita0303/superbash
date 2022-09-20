package io

class ConsoleContentOutput: ContentOutput {
    private val prompt = "$> "
    private fun printPrompt() {
        println(prompt)
    }
    override fun printContent(content: String) {
        println(content)
        printPrompt()
    }
}