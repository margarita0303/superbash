package parsing

import Context
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class CLIEntityCreatorTest {
    private val context = Mockito.mock(Context::class.java)
    private val keywordCreator = Mockito.mock(KeywordCreator::class.java)
    private val cliEntityCreator = CLIEntityCreator()

    @Test
    fun testCreateArgument() {
        val someArgument = "some argument"
        val resultEntityArgument = cliEntityCreator.createArgument(someArgument)
        Assert.assertEquals(someArgument, resultEntityArgument.getArgument())
    }

    @Test
    fun testCreateKeyword() {
        val someKeyword = "keyword"
        Mockito.doNothing().`when`(keywordCreator).createKeyword(someKeyword, context)
        cliEntityCreator.createKeyword(someKeyword, context)

        Mockito.verify(keywordCreator.createKeyword(someKeyword, context))
    }
}