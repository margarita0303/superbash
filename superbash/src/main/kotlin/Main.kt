import io.ConsoleContentInput
import io.ConsoleContentOutput

fun main(args: Array<String>) {
    val input = ConsoleContentInput()
    val output = ConsoleContentOutput()

    val cliManager = CLIManager()

    while (true) {
        output.printPrompt()
        val query = input.getContent()
        val result = cliManager.run(query)

        if (result.output.isPresent) {
            output.printContent(result.output.get())
        }

        if (result.shouldExit) {
            return
        }
    }
}
