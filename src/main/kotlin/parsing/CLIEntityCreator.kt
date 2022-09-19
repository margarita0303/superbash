package parsing

import Context
import entities.Argument
import entities.CLIArgument
import entities.Initialization
import entities.Keyword

class CLIEntityCreator {
    private val keywordCreator = KeywordCreator()

    fun createInitialization(expression: String): Initialization = Initialization()

    fun createArgument(argument: String): Argument = CLIArgument(argument)

    fun createKeyword(keyword: String, context: Context): Keyword = keywordCreator.createKeyword(keyword, context)
}
