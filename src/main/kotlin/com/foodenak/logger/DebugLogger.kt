package com.foodenak.logger

import java.util.regex.Pattern

/**
 * [Logger] for debugging, synthetically extract caller class name as tag if tag is null
 * Do not use [this][DebugLogger] with proguard enabled
 */
abstract class DebugLogger() : Logger() {

    override public final fun getTag(): String {
        val tag = super.getTag()
        if (tag != null) return tag
        val trace = Throwable().stackTrace
        if (trace.size <= CALL_STACK_INDEX) throw IllegalStateException("Using proguard?")
        val className = trace[CALL_STACK_INDEX].className
        val matcher = ANONYMOUS_CLASS.matcher(className)
        val name = if (matcher.find()) matcher.replaceAll("") else className
        return name.substring(name.lastIndexOf('.') + 1)
    }

    companion object {
        private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
        private const val CALL_STACK_INDEX = 5
    }
}