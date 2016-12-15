package com.foodenak.logger

import java.util.*

object Log {

    /** Log with [Priority.VERBOSE] */
    @JvmStatic fun v(throwable: Throwable? = null, message: String? = null, args: Array<out Any?> = arrayOf()) {
        engine.v(throwable, message, args)
    }

    /**
     * Log with [Priority.DEBUG]
     */
    @JvmStatic fun d(throwable: Throwable? = null, message: String? = null, args: Array<out Any?> = arrayOf()) {
        engine.d(throwable, message, args)
    }

    /**
     * Log with [Priority.INFO]
     */
    @JvmStatic fun i(throwable: Throwable? = null, message: String? = null, args: Array<out Any?> = arrayOf()) {
        engine.i(throwable, message, args)
    }

    /**
     * Log with [Priority.WARNING]
     */
    @JvmStatic fun w(throwable: Throwable? = null, message: String? = null, args: Array<out Any?> = arrayOf()) {
        engine.w(throwable, message, args)
    }

    /**
     * Log with [Priority.ERROR]
     */
    @JvmStatic fun e(throwable: Throwable? = null, message: String? = null, args: Array<out Any?> = arrayOf()) {
        engine.e(throwable, message, args)
    }

    /**
     * Log with [Priority.ASSERT]
     */
    @JvmStatic fun wtf(throwable: Throwable? = null, message: String? = null, args: Array<out Any?> = arrayOf()) {
        engine.wtf(throwable, message, args)
    }

    /**
     * Log with [priority]
     */
    @JvmStatic fun log(priority: Priority, throwable: Throwable? = null, message: String?, args: Array<out Any?> = arrayOf()) {
        engine.log(priority, throwable, message, args)
    }

    /** Set tag to be used on the next logging */
    @JvmStatic fun tag(tag: String): Logger {
        val loggers = loggerArray
        for (logger in loggers) logger.explicitTag.set(tag)
        return engine
    }

    internal val emptyLoggerArray = arrayOf<Logger>()
    internal val loggerList = ArrayList<Logger>()
    internal var loggerArray = emptyLoggerArray

    @JvmStatic fun add(logger: Logger) {
        if (logger == engine) throw IllegalArgumentException("Can't add engine into itself.")
        synchronized(loggerList) {
            loggerList.add(logger)
            loggerArray = loggerList.toTypedArray()
        }
    }

    @JvmStatic fun remove(logger: Logger) {
        synchronized(loggerList) {
            if (!loggerList.remove(logger)) throw IllegalStateException("Logger is not added yet.")
            loggerArray = if (loggerList.isEmpty()) emptyLoggerArray else loggerList.toTypedArray()
        }
    }

    @JvmStatic fun asLogger(): Logger = engine

    @JvmStatic fun loggers(): List<Logger> = listOf(*loggerArray)

    @JvmStatic private val engine = object : Logger() {
        override fun v(throwable: Throwable?, message: String?, args: Array<out Any?>) {
            val loggers = loggerArray
            for (logger in loggers) logger.v(throwable, message, args)
        }

        override fun d(throwable: Throwable?, message: String?, args: Array<out Any?>) {
            val loggers = loggerArray
            for (logger in loggers) logger.d(throwable, message, args)
        }

        override fun i(throwable: Throwable?, message: String?, args: Array<out Any?>) {
            val loggers = loggerArray
            for (logger in loggers) logger.i(throwable, message, args)
        }

        override fun w(throwable: Throwable?, message: String?, args: Array<out Any?>) {
            val loggers = loggerArray
            for (logger in loggers) logger.w(throwable, message, args)
        }

        override fun e(throwable: Throwable?, message: String?, args: Array<out Any?>) {
            val loggers = loggerArray
            for (logger in loggers) logger.e(throwable, message, args)
        }

        override fun wtf(throwable: Throwable?, message: String?, args: Array<out Any?>) {
            val loggers = loggerArray
            for (logger in loggers) logger.wtf(throwable, message, args)
        }

        override fun log(priority: Priority, throwable: Throwable?, message: String?, args: Array<out Any?>) {
            val loggers = loggerArray
            for (logger in loggers) logger.log(priority, throwable, message, args)
        }

        override fun println(priority: Priority, tag: String?, message: String) {
            throw AssertionError("Missing override!")
        }
    }
}