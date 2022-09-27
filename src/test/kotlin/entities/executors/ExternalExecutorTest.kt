package entities.executors

import Context
import entities.Argument
import entities.CLIArgument
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.opentest4j.TestAbortedException
import org.powermock.modules.junit4.PowerMockRunner
import java.nio.file.Paths
import kotlin.io.path.absolute

@RunWith(PowerMockRunner::class)
class ExternalExecutorTest {
    enum class OS {
        WINDOWS, LINUX
    }

    fun getOS(): OS? {
        val os = System.getProperty("os.name").toLowerCase()
        return when {
            os.contains("win") -> {
                OS.WINDOWS
            }
            os.contains("nix") || os.contains("nux") || os.contains("aix") -> {
                OS.LINUX
            }
            else -> null
        }
    }

    private val curPath = Paths.get("/")
    private val context = Context()
    private var command = ""

    init {
        when (getOS()) {
            OS.WINDOWS -> {
                context.directory = curPath.toString()
                context.variables["PATH"] = "C:\\Windows\\;C:\\Windows\\System\\;C:\\Windows\\System32"
                command = "ECHO.exe"
            }

            OS.LINUX -> {
                context.directory = curPath.toString()
                context.variables["PATH"] = "/bin"
                command = "echo"
            }

            else -> throw TestAbortedException("Unknown OS for testing")
        }
    }

    @Test
    fun testCatFile1() {
        val externalExecutor = ExternalExecutor(curPath, context, Paths.get(command))
        val result = externalExecutor.execute(listOf(CLIArgument("123")))
        Assert.assertEquals("123\n", result.get())
    }
}