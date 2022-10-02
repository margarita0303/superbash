package parsing

import Context
import parsing.helpers.ImportantChars
import parsing.helpers.splitBySpaceAndPipe

class VariablesSubstitutor {
    private val bashVariableRegex = "\\$[a-zA-Z_][a-zA-Z_0-9]*".toRegex()

    fun substitute(content: String, context: Context): String {
        val splittedContent = splitBySpaceAndPipe(content)
        val substitutedContent = splittedContent.map {
            when {
                it.isDoubleQuotedString() || it.isNotQuotedString() -> substituteOneString(it, context)
                else -> it
            }
        }
        return substitutedContent.joinToString(" ")
    }

    private fun substituteOneString(content: String, context: Context): String {
        var substitutedContent = content
        bashVariableRegex.findAll(content).toMutableList().reversed().forEach { match ->
            substitutedContent = context.variables[match.value.drop(1)]
                ?.let { substitutedContent.replaceRange(match.range, it) }
                ?: substitutedContent
        }
        return substitutedContent
    }

    private fun String.isDoubleQuotedString(): Boolean {
        return first() == ImportantChars.QUOTE_MARK_CHAR && last() == ImportantChars.QUOTE_MARK_CHAR
    }

    private fun String.isNotQuotedString(): Boolean {
        return !contains(ImportantChars.QUOTE_MARK_CHAR) && !contains(ImportantChars.SINGLE_MARK_CHAR)
    }
}
