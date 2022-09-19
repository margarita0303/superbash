package entities

class CLIArgument(private val argument: String): Argument {
    override fun getArgument(): String {
        return argument
    }
}