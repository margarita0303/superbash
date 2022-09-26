package parsing

import Context
import entities.Argument
import entities.CLIArgument
import entities.Initialization
import entities.Keyword


/**
 * A class that can create all possible *[CLIEntity]*
 */
class CLIEntityCreator {
    private val keywordCreator = KeywordCreator()

    /**
     * Creates [Initialization] by given *expression*
     *
     * @return [Initialization]
     */
    fun createInitialization(expression: String): Initialization = Initialization()

    /**
     * Creates [CLIArgument] by given *argument*
     *
     * @return [CLIArgument]
     */
    fun createArgument(argument: String): Argument = CLIArgument(argument)

    /**
     * Delegates creation [Keyword] [KeywordCreator]
     *
     * @return [Keyword]
     */
    fun createKeyword(keyword: String, context: Context): Keyword = keywordCreator.createKeyword(keyword, context)
}
