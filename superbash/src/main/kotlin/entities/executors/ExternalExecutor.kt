package entities.executors

import Context
import entities.Argument
import entities.Keyword
import exceptions.ParseException
import exceptions.RunException
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.exists
import kotlin.io.path.name

class ExternalExecutor(private val curPath: Path, private val context: Context, private val relBinaryPath: Path): Keyword {
    private val binaryPath = getBinaryPath() ?: throw ParseException("No such binary: ${relBinaryPath.name}")

    override fun execute(arguments: List<Argument>): Optional<String> {
        val process = ProcessBuilder(binaryPath.name, *arguments.map { it.getArgument() }.toTypedArray())
            .directory(curPath.toFile())
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()

        process.waitFor()
        val output = process.inputStream.bufferedReader().readText()

        return Optional.of(output)
    }

    private fun getBinaryPath() : Path? {
        tryGetInPathVariable()?.let { return it }
        tryGetRelative()?.let { return it }

        return null
    }

    private fun tryGetInPathVariable() : Path? {
        val pathVariables = context.variables["PATH"]?.split(':')

        if (pathVariables != null) {
            for (pathString in pathVariables) {
                val path = Paths.get(pathString)
                val files = path.toFile().listFiles() ?: continue
                for (file in files) {
                    if (file.name.equals(relBinaryPath.name)) return file.toPath()
                }

            }
        }

        return null
    }

    private fun tryGetRelative() : Path? {
        val path = if (relBinaryPath.isAbsolute) {
            relBinaryPath
        } else {
            Paths.get(curPath.toString() + relBinaryPath.toString())
        }
        if (path.exists()) return path

        return null
    }
}