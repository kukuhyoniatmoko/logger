package com.foodenak.logger

import java.io.PrintWriter
import java.io.StringWriter

abstract class Logger {
    open internal fun v(throwable: Throwable? = null, tag: String? = null, message: String? = null, args: Array<String> = arrayOf()) {
        prepare(Priority.VERBOSE, throwable, tag, message, args)
    }

    open internal fun d(throwable: Throwable? = null, tag: String? = null, message: String? = null, args: Array<String> = arrayOf()) {
        prepare(Priority.DEBUG, throwable, tag, message, args)
    }

    open internal fun i(throwable: Throwable? = null, tag: String? = null, message: String? = null, args: Array<String> = arrayOf()) {
        prepare(Priority.INFO, throwable, tag, message, args)
    }

    open internal fun w(throwable: Throwable? = null, tag: String? = null, message: String? = null, args: Array<String> = arrayOf()) {
        prepare(Priority.WARNING, throwable, tag, message, args)
    }

    open internal fun e(throwable: Throwable? = null, tag: String? = null, message: String? = null, args: Array<String> = arrayOf()) {
        prepare(Priority.ERROR, throwable, tag, message, args)
    }

    open internal fun wtf(throwable: Throwable? = null, tag: String? = null, message: String? = null, args: Array<String> = arrayOf()) {
        prepare(Priority.ASSERT, throwable, tag, message, args)
    }

    open internal fun log(priority: Priority, throwable: Throwable? = null, tag: String? = null, message: String? = null, args: Array<String> = arrayOf()) {
        prepare(priority, throwable, tag, message, args)
    }

    open fun isLoggable(priority: Priority): Boolean = true

    private fun prepare(priority: Priority, throwable: Throwable? = null, tag: String? = null, message: String? = null, args: Array<String> = arrayOf()) {
        if (!isLoggable(priority)) return
        val preparedMessage = (if (message == null || message.length <= 0) {
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
        log(priority, tag, preparedMessage)
    }

    abstract fun log(priority: Priority, tag: String?, message: String)

    private fun extractStackTrace(throwable: Throwable): String? {
        val writer = StringWriter(256)
        val printer = PrintWriter(writer, false)
        throwable.printStackTrace(printer)
        printer.flush()
        return writer.toString()
    }
}