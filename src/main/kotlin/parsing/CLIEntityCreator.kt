package parsing

import Context
import entities.Argument
import entities.CLIArgument
import entities.Initialization
import entities.Keyword

class CLIEntityCreator {
    private val keywordCreator = KeywordCreator()

    /**
     * Создаёт *Initialization* по переданному *expression*
     */
    fun createInitialization(expression: String): Initialization = Initialization()

    /**
     * Создаёт *CLIArgument* по переданному *argument*
     */
    fun createArgument(argument: String): Argument = CLIArgument(argument)

    /**
     * Делегирует создание *Keyword* *KeywordCreator*
     */
    fun createKeyword(keyword: String, context: Context): Keyword = keywordCreator.createKeyword(keyword, context)
}
