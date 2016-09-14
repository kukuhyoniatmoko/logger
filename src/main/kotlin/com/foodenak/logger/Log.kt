package com.foodenak.logger

import java.util.*

object Log {
    fun v(throwable: Throwable? = null, tag: String? = null, message: String? = null, args: Array<String> = arrayOf()) {
        LOGGERS.v(throwable, tag, message, args)
    }

    fun d(throwable: Throwable? = null, tag: String? = null, message: String? = null, args: Array<String> = arrayOf()) {
        LOGGERS.d(throwable, tag, message, args)
    }

    fun i(throwable: Throwable? = null, tag: String? = null, message: String? = null, args: Array<String> = arrayOf()) {
        LOGGERS.i(throwable, tag, message, args)
    }

    fun w(throwable: Throwable? = null, tag: String? = null, message: String? = null, args: Array<String> = arrayOf()) {
        LOGGERS.w(throwable, tag, message, args)
    }

    fun e(throwable: Throwable? = null, tag: String? = null, message: String? = null, args: Array<String> = arrayOf()) {
        LOGGERS.e(throwable, tag, message, args)
    }

    fun wtf(throwable: Throwable? = null, tag: String? = null, message: String? = null, args: Array<String> = arrayOf()) {
        LOGGERS.wtf(throwable, tag, message, args)
    }

    fun log(priority: Priority, throwable: Throwable? = null, tag: String? = null, message: String? = null, args: Array<String> = arrayOf()) {
        LOGGERS.log(priority, throwable, tag, message, args)
    }

    private val EMPTY_LOGGER_ARRAY = arrayOf<Logger>()

    private val LOGGER_LIST = HashSet<Logger>()
    private var LOGGER_ARRAY = EMPTY_LOGGER_ARRAY

    fun add(logger: Logger) {
        if (logger == LOGGERS) throw IllegalArgumentException()
        synchronized(LOGGER_LIST) {
            LOGGER_LIST.add(logger)
            LOGGER_ARRAY = LOGGER_LIST.toTypedArray()
        }
    }

    fun remove(logger: Logger) {
        synchronized(LOGGER_LIST) {
            if (!LOGGER_LIST.remove(logger)) throw IllegalStateException()
            LOGGER_ARRAY = if (LOGGER_LIST.isEmpty()) EMPTY_LOGGER_ARRAY else LOGGER_LIST.toTypedArray()
        }
    }

    fun asLogger(): Logger = LOGGERS

    fun loggers(): List<Logger> = listOf(*LOGGER_ARRAY)

    private val LOGGERS = object : Logger() {
        override fun v(throwable: Throwable?, tag: String?, message: String?, args: Array<String>) {
            val loggers = LOGGER_ARRAY
            for (logger in loggers) logger.v(throwable, tag, message, args)
        }

        override fun d(throwable: Throwable?, tag: String?, message: String?, args: Array<String>) {
            val loggers = LOGGER_ARRAY
            for (logger in loggers) logger.d(throwable, tag, message, args)
        }

        override fun i(throwable: Throwable?, tag: String?, message: String?, args: Array<String>) {
            val loggers = LOGGER_ARRAY
            for (logger in loggers) logger.i(throwable, tag, message, args)
        }

        override fun w(throwable: Throwable?, tag: String?, message: String?, args: Array<String>) {
            val loggers = LOGGER_ARRAY
            for (logger in loggers) logger.w(throwable, tag, message, args)
        }

        override fun e(throwable: Throwable?, tag: String?, message: String?, args: Array<String>) {
            val loggers = LOGGER_ARRAY
            for (logger in loggers) logger.e(throwable, tag, message, args)
        }

        override fun wtf(throwable: Throwable?, tag: String?, message: String?, args: Array<String>) {
            val loggers = LOGGER_ARRAY
            for (logger in loggers) logger.wtf(throwable, tag, message, args)
        }

        override fun log(priority: Priority, throwable: Throwable?, tag: String?, message: String?, args: Array<String>) {
            val loggers = LOGGER_ARRAY
            for (logger in loggers) logger.log(priority, throwable, tag, message, args)
        }

        override fun log(priority: Priority, tag: String?, message: String) {
            throw AssertionError("Missing override!")
        }
    }
}