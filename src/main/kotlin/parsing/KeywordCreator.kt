package parsing

import Context
import entities.Keyword
import entities.executors.*


/**
 * A class that can create all possible [Keyword]
 */
class KeywordCreator {
    /**
     * Creates [Keyword] by given *keyword* and *[Context]*
     *
     * @return [Keyword]
     */
    fun createKeyword(keyword: String, context: Context): Keyword = when(keyword) {
        "cat"   -> CatExecutor()
        "echo"  -> EchoExecutor()
        "wc"    -> WCExecutor()
        "pwd"   -> PwdExecutor()
        else    -> ExternalExecutor()  // throw ParseException
    }
}
