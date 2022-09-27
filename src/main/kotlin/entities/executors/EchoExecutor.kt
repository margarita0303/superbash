package entities.executors

import entities.Argument
import entities.Keyword
import java.util.*

class EchoExecutor: Keyword {
    override fun execute(arguments: List<Argument>): Optional<String> {
        val output = arguments.map { it.getArgument() }.joinToString(" ") + "\n"
        return Optional.of(output)
    }
}
