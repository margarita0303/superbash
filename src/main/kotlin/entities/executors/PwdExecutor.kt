package entities.executors

import entities.Argument
import entities.Keyword
import java.nio.file.Path
import java.util.*
import kotlin.io.path.name

class PwdExecutor(private val curPath: Path): Keyword {
    override fun execute(arguments: List<Argument>): Optional<String> = Optional.of(curPath.name)
}
