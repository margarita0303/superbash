package io

class ConsoleContentInput: ContentInput {
    override fun getContent(): String {
        return readln()
    }
}