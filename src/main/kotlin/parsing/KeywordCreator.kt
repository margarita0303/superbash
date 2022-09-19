package parsing

import Context
import entities.Keyword
import entities.executors.*

class KeywordCreator {

    fun createKeyword(keyword: String, context: Context): Keyword = when(keyword) {
        "cat"   -> CatExecutor()
        "echo"  -> EchoExecutor()
        "wc"    -> WCExecutor()
        "pwd"   -> PwdExecutor()
        else    -> ExternalExecutor()  // throw ParseException
    }
}
