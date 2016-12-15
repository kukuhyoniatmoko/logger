package com.foodenak.logger

import java.io.PrintWriter
import java.io.StringWriter

abstract class Logger {

    /**
     * Print message into log output
     *
     * @param priority Log [Priority]
     * @param tag Log tag
     * @param message Log message
     */
    abstract protected fun println(priority: Priority, tag: String?, message: String)

    /**
     * Check if [Priority] is loggable
     *
     * @return true if the [Priority] is loggable
     */
    open fun isLoggable(priority: Priority): Boolean = true

    internal val explicitTag = ThreadLocal<String>()

    open internal fun v(throwable: Throwable? = null, message: String? = null, args: Array<out Any?> = arrayOf()) {
        prepare(Priority.VERBOSE, throwable, message, args)
    }

    open internal fun d(throwable: Throwable? = null, message: String? = null, args: Array<out Any?> = arrayOf()) {
        prepare(Priority.DEBUG, throwable, message, args)
    }

    open internal fun i(throwable: Throwable? = null, message: String? = null, args: Array<out Any?> = arrayOf()) {
        prepare(Priority.INFO, throwable, message, args)
    }

    open internal fun w(throwable: Throwable? = null, message: String? = null, args: Array<out Any?> = arrayOf()) {
        prepare(Priority.WARNING, throwable, message, args)
    }

    open internal fun e(throwable: Throwable? = null, message: String? = null, args: Array<out Any?> = arrayOf()) {
        prepare(Priority.ERROR, throwable, message, args)
    }

    open internal fun wtf(throwable: Throwable? = null, message: String? = null, args: Array<out Any?> = arrayOf()) {
        prepare(Priority.ASSERT, throwable, message, args)
    }

    open internal fun log(priority: Priority, throwable: Throwable? = null, message: String? = null, args: Array<out Any?> = arrayOf()) {
        prepare(priority, throwable, message, args)
    }

    open internal fun getTag(): String? {
        val tag = explicitTag.get()
        if (tag != null) explicitTag.remove()
        return tag
    }

    private fun prepare(priority: Priority, throwable: Throwable? = null, message: String? = null, args: Array<out Any?> = arrayOf()) {
        val tag = getTag()
        if (!isLoggable(priority)) return
        val preparedMessage = (if (message == null || message.isEmpty()) {
            if (throwable == null) null else {
                extractStackTrace(throwable)
            }
        } else {
            if (throwable == null) {
                String.format(message, *args)
            } else {
                "${String.format(message, *args)}\n${extractStackTrace(throwable)}"
            }
        }) ?: return
        println(priority, tag, preparedMessage)
    }

    private fun extractStackTrace(throwable: Throwable): String? {
        val writer = StringWriter(256)
        val printer = PrintWriter(writer, false)
        throwable.printStackTrace(printer)
        printer.flush()
        return writer.toString()
    }
}