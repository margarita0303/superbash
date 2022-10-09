package entities.executors.utils

import java.io.FileNotFoundException
import java.lang.Exception
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.name


/**
 * Method to read a file
 * @param relPath stores path to file
 * @return file content
 */
internal fun tryRead(curPath: Path, relPath: String, command: String): String {
    return try {
        val file = if (isAbsolute(relPath)) {
            Paths.get(relPath).toFile()
        } else {
            Paths.get(curPath.name + relPath).toFile()
        }
        file.readText()
    } catch (e: FileNotFoundException) {
        "$command: ${relPath}: No such file or directory\n"
    }
}

/**
 * Method to check is path is absolute
 * @param path stores path to file
 * @return true if path exists and it is absolute
 */
private fun isAbsolute(path: String): Boolean {
    return try {
        val pathCheck = Paths.get(path)
        pathCheck.isAbsolute
    } catch (e: Exception) {
        false
    }
}
