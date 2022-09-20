package io

/**
 * Class to manage console output
 */
class ConsoleContentOutput : ContentOutput {
    /**
     * Prompt value
     */
    private val prompt = "$> "

    /**
     * Method to print a prompt
     */
    private fun printPrompt() {
        println(prompt)
    }

    /**
     * Implementation of `printContent` interface method
     */
    override fun printContent(content: String) {
        println(content)
        printPrompt()
    }
}
