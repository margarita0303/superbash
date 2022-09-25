package entities.executors

import entities.Argument
import entities.Keyword
import java.util.*

class EchoExecutor: Keyword {
    override fun execute(arguments: List<Argument>): Optional<String> {
        var s = ""
        for (argument in arguments) {
            s += argument.getArgument()
        }

        return Optional.of(s)
    }
}