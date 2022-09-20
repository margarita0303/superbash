package io

/**
 * Class to manage console input
 */
class ConsoleContentInput : ContentInput {
    /**
     * Implementation of `getContent` interface method
     */
    override fun getContent(): String = return readln()
}
