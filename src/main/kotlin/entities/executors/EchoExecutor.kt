package entities.executors

import entities.Argument
import entities.Keyword
import java.util.*

class EchoExecutor: Keyword {
    override fun execute(arguments: List<Argument>): Optional<String> {
        var output = ""
        for (argument in arguments) {
            output += argument.getArgument()
        }

        return Optional.of(output)
    }
}