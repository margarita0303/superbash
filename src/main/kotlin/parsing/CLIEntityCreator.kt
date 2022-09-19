package parsing

import Context
import entities.Argument
import entities.Initialization
import entities.Keyword

class CLIEntityCreator {

    fun createInitialization(expression: String): Initialization = TODO()

    fun createArgument(argument: String): Argument = TODO()

    fun createKeyword(keyword: Keyword, context: Context): Keyword = TODO()
}