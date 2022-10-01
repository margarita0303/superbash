import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CLIManagerTest {
    private val manager = CLIManager()

    @Test
    fun testEcho() {
        val result = manager.run("echo 123")
        Assert.assertEquals("123", result.output.get())
        Assert.assertFalse(result.shouldExit)
    }

    @Test
    fun testEchoSplitted() {
        val result = manager.run("echo 1 2 3")
        Assert.assertEquals("1 2 3", result.output.get())
        Assert.assertFalse(result.shouldExit)
    }

}