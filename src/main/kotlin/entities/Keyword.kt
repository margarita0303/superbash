package entities
import java.util.Optional
interface Keyword: CLIEntity {
    fun execute(arguments: List<Argument>): Optional<String>
}
