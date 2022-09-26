package parsing

import Context
import entities.CLIEntity
import exceptions.ParseException


/**
 * A class that can parse user input
 */
class ContentParser {
    private enum class State {  // quotation marks or not
        STANDARD,
        SINGLE_MARK = '\'',
        QUOTE_MARK  = '"'
    }

    private class MarksStateHolder {
        var state = State.STANDARD
        fun singleMark() = when(state) {
            State.STANDARD -> { state = State.SINGLE_MARK }
            State.SINGLE_MARK -> { state = State.STANDARD }
            else -> {}
        }

        fun quoteMark() = when(state) {
            State.STANDARD -> { state = State.QUOTE_MARK }
            State.QUOTE_MARK -> { state = State.STANDARD }
            else -> {}
        }

        val isStandard: Boolean
            get() = state == State.STANDARD
    }

    private val cliEntityCreator = CLIEntityCreator()

    private fun splitBySpace(input: String): List<String> {
        val builder = StringBuilder()
        val splitters = mutableListOf<String>()
        val marksStateHolder = MarksStateHolder()
        input.forEach { ch -> when(ch) {
            State.SINGLE_MARK -> {
                marksStateHolder.singleMark()
                builder.append(ch)
            }
            State.QUOTE_MARK -> {
                marksStateHolder.quoteMark()
                builder.append(ch)
            }
            ' ' -> {
                if (builder.isNotEmpty()) {
                    if (marksStateHolder.isStandard) {
                        splitters.add(builder.toString())
                        builder.clear()
                    } else builder.append(ch)
                }
            }
            else -> builder.append(ch)
        }}
        return splitters
    }

    /**
     * Accepts a string with the user's request and *context* (which just scrolls further)
     *
     * @return list of [CLIEntity], it uses [CLIEntityCreator] to create
     *
     * @throws ParseException if error occurred while trying to create any [CLIEntity]
     */
    fun parse(input: String, context: Context): List<CLIEntity> {
        val splitters = splitBySpace(input)
        val cliEntities = mutableListOf<CLIEntity>()
        val firstEntity = try {
             cliEntityCreator.createKeyword(splitters.first(), context)
        } catch (_: ParseException) {
            cliEntityCreator.createInitialization(splitters.first())
        }
        cliEntities.add(firstEntity)
        splitters.drop(1).forEach { arg ->
            val entity = cliEntityCreator.createArgument(arg)
            cliEntities.add(entity)
        }
        return cliEntities
    }
}
