package com.foodenak.logger

import java.util.regex.Pattern

abstract class DebugLogger() : Logger() {
    override final fun log(priority: Priority, tag: String?, message: String) {
        val preparedTag: String
        if (tag == null) {
            val trace = Throwable().stackTrace
            if (trace.size <= CALL_STACK_INDEX) throw IllegalStateException("Using proguard?")
            val className = trace[CALL_STACK_INDEX].className
            val matcher = ANONYMOUS_CLASS.matcher(className)
            val name = if (matcher.find()) matcher.replaceAll("") else className
            preparedTag = name.substring(name.lastIndexOf('.') + 1)
        } else {
            preparedTag = tag
        }
        debugLog(priority, preparedTag, message)
    }

    abstract fun debugLog(priority: Priority, preparedTag: String, message: String)

    companion object {
        private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
        private const val CALL_STACK_INDEX = 5
    }
}