package entities.executors

import entities.Argument
import entities.Keyword
import entities.PipeArgument
import entities.executors.utils.tryRead
import java.nio.file.Path
import java.util.*

/**
 * Class to execute `cat` command
 * @param curPath stores current path from context
 */
class CatExecutor(private val curPath: Path): Keyword {
    /**
     * Method to execute `cat`
     * @param arguments stores files to cat
     * @return all files contents
     */
    override fun execute(arguments: List<Argument>): Optional<String> {
        val updatedArguments = if (arguments.size > 1 && arguments.last() is PipeArgument) {
            arguments.dropLast(1)
        } else arguments
        if (updatedArguments.isEmpty()) {
            return Optional.empty()
        }

        var output = ""
        for (argument in updatedArguments) {
            output += tryRead(curPath, argument.getArgument(), "cat")
        }
        return Optional.of(output)
    }
}
