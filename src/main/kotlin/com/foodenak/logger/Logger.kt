package com.foodenak.logger

import java.io.PrintWriter
import java.io.StringWriter

abstract class Logger {

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

    open fun isLoggable(priority: Priority): Boolean = true

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

    abstract protected fun println(priority: Priority, tag: String?, message: String)

    private fun extractStackTrace(throwable: Throwable): String? {
        val writer = StringWriter(256)
        val printer = PrintWriter(writer, false)
        throwable.printStackTrace(printer)
        printer.flush()
        return writer.toString()
    }
}