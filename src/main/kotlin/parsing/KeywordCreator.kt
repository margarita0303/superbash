package parsing

import Context
import entities.Keyword
import entities.executors.*

class KeywordCreator {
    /**
     * Создаёт *Keyword* по переданной *keyword* и *context*
     */
    fun createKeyword(keyword: String, context: Context): Keyword = when(keyword) {
        "cat"   -> CatExecutor()
        "echo"  -> EchoExecutor()
        "wc"    -> WCExecutor()
        "pwd"   -> PwdExecutor()
        else    -> ExternalExecutor()  // throw ParseException
    }
}
