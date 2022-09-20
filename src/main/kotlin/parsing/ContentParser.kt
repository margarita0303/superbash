package parsing

import Context
import entities.CLIEntity
import exceptions.ParseException

class ContentParser {
    private class MarksStateHolder {
        private enum class State {  // quotation marks or not
            STANDARD,
            ONE_MARK,   // '
            TWO_MARK    // "
        }

        var state = State.STANDARD
        fun one() = when(state) {
            State.STANDARD -> { state = State.ONE_MARK }
            State.ONE_MARK -> { state = State.STANDARD }
            else -> {}
        }

        fun two() = when(state) {
            State.STANDARD -> { state = State.TWO_MARK }
            State.TWO_MARK -> { state = State.STANDARD }
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
            '\'' -> {
                marksStateHolder.one()
                builder.append(ch)
            }
            '"' -> {
                marksStateHolder.two()
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
     * Принимает строчку с запросом пользователя и context (который просто прокидывается дальше)
     *
     * Возвращает список сущностей ICLIEntity, для создания которых он использует CLIEntityCreator
     *
     * @throws ParseException если возникла ошибка при попытке создеать какое либо CLIEntity
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
