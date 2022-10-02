import entities.Argument
import entities.CLIEntity
import entities.Initialization
import entities.Keyword
import exceptions.Constants
import parsing.ContentParser
import parsing.VariablesSubstitutor
import java.security.Key

import java.util.*

/**
 * Class to manage interaction with user
 */
class CLIManager(startDirectory: String = "/") {
    private val context = Context()

    init {
        context.directory = startDirectory
        context.variables["PATH"] = ""
    }

    private val parser = ContentParser()
    private val substitutor = VariablesSubstitutor()

    /**
     * Method to execute query
     * @param query from user
     * @return output for query and flag to handle `exit`
     */
    fun run(query: String): Optional<String> = try {
        val substitutedContent = substitutor.substitute(query, context)
        val parsedTokens = parser.parse(substitutedContent, context)
        execute(parsedTokens)
    } catch (ex: Exception) {
        Optional.of((ex.message ?: Constants.UNKNOWN_ERROR) + '\n')
    }

    /**
     * Method to handle parsed entities
     * @param tokens List<CLIEntity> representing parsed command
     * @return Optional<String> containing possible result of an execution
     */
    private fun execute(tokens: List<CLIEntity>): Optional<String> {
        if (tokens.isEmpty()) {
            return Optional.empty()
        }

        when {
            tokens.first() is Keyword -> {
                return executeKeyword(tokens)
            }

            tokens.first() is Initialization -> {
                return executeInitialization(tokens)
            }
        }

        return Optional.empty()
    }

    private fun executeKeyword(tokens: List<CLIEntity>): Optional<String> {
        val firstToken = tokens.first() as Keyword
        return firstToken.execute(tokens.drop(1).map { it as Argument })
    }

    private fun executeInitialization(tokens: List<CLIEntity>): Optional<String> {
        val firstToken = tokens.first() as Initialization
        context.variables[firstToken.valueName] = firstToken.value
        return Optional.empty()
    }
}
