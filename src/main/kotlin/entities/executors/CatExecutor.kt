package entities.executors

import entities.Argument
import entities.Keyword
import exceptions.RunException
import java.nio.file.InvalidPathException
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.name

class CatExecutor(private val curPath: Path): Keyword {
    override fun execute(arguments: List<Argument>): Optional<String> {
        if (arguments.isEmpty()) {
            return Optional.empty()
        }

        var output = ""

        for (argument in arguments) {
            output += tryRead(argument.getArgument())
        }

        return Optional.of(output)
    }

    private fun tryRead(relPath: String): String {
        return try {
            val file = Paths.get(curPath.name + relPath).toFile()
            file.readText()
        } catch (e: InvalidPathException) {
            "cat: ${relPath}: No such file or directory\n"
        }
    }
}