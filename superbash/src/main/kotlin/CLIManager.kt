import entities.*
import exceptions.Constants
import exceptions.RunException
import io.ConsoleContentInput
import io.ConsoleContentOutput
import parsing.ContentParser
import java.security.Key
import java.util.*

/**
 * Class to manage interaction with user
 */
class CLIManager {
    private val context = Context()
    private val parser = ContentParser()

    /**
     * Method to execute query
     * @param query from user
     * @return output for query and flag to handle `exit`
     */
    fun run(query: String): ExecutionResult = try {
        val parsedTokens = parser.parse(query, context)
        execute(parsedTokens)
    } catch (ex: Exception) {
        ExecutionResult(Optional.of((ex.message ?: Constants.UNKNOWN_ERROR) + '\n'))
    }

    /**
     * Method to handle parsed entities
     * @param tokens List<CLIEntity> representing parsed command
     * @return ExecutionResult containing possible result of an execution
     */
    private fun execute(tokens: List<CLIEntity>): ExecutionResult {
        if (tokens.isEmpty()) {
            return ExecutionResult(Optional.empty())
        }

        if (tokens.size == 1 && tokens.first() is Exit) {
            return ExecutionResult(Optional.of(""), shouldExit = true)
        }

        when {
            tokens.first() is Keyword -> {
                return executeKeyword(tokens)
            }

            tokens.first() is Initialization -> {
                return executeInitialization(tokens)
            }
        }

        return ExecutionResult(Optional.empty())
    }

    private fun executeKeyword(tokens: List<CLIEntity>): ExecutionResult {
        val firstToken = tokens.first() as Keyword
        return ExecutionResult(firstToken.execute(tokens.drop(1).map { it as Argument }))
    }

    private fun executeInitialization(tokens: List<CLIEntity>): ExecutionResult {
        val firstToken = tokens.first() as Initialization
        context.variables[firstToken.valueName] = firstToken.value
        return ExecutionResult(Optional.empty())
    }
}
