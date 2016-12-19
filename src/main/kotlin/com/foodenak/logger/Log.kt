package com.foodenak.logger

object Log : Logger() {

    /**
     * Log with [Priority.VERBOSE]
     */
    override fun v(throwable: Throwable?, message: String?, args: Array<out Any?>) {
        val loggers = loggerArray
        for (logger in loggers) logger.v(throwable, message, args)
    }

    /**
     * Log with [Priority.DEBUG]
     */
    override fun d(throwable: Throwable?, message: String?, args: Array<out Any?>) {
        val loggers = loggerArray
        for (logger in loggers) logger.d(throwable, message, args)
    }

    /**
     * Log with [Priority.INFO]
     */
    override fun i(throwable: Throwable?, message: String?, args: Array<out Any?>) {
        val loggers = loggerArray
        for (logger in loggers) logger.i(throwable, message, args)
    }

    /**
     * Log with [Priority.WARNING]
     */
    override fun w(throwable: Throwable?, message: String?, args: Array<out Any?>) {
        val loggers = loggerArray
        for (logger in loggers) logger.w(throwable, message, args)
    }

    /**
     * Log with [Priority.ERROR]
     */
    override fun e(throwable: Throwable?, message: String?, args: Array<out Any?>) {
        val loggers = loggerArray
        for (logger in loggers) logger.e(throwable, message, args)
    }

    /**
     * Log with [Priority.ASSERT]
     */
    override fun wtf(throwable: Throwable?, message: String?, args: Array<out Any?>) {
        val loggers = loggerArray
        for (logger in loggers) logger.wtf(throwable, message, args)
    }

    /**
     * Log with [priority]
     * @param priority Priority of Log
     */
    override fun log(priority: Priority, throwable: Throwable?, message: String?, args: Array<out Any?>) {
        val loggers = loggerArray
        for (logger in loggers) logger.log(priority, throwable, message, args)
    }

    /**
     * Don't use this method, use [d],[i],[w],[e],[wtf], or [log] instead
     *
     * @throws [AssertionError]
     */
    override fun println(priority: Priority, tag: String?, message: String) {
        throw AssertionError("Missing override!")
    }

    /**
     * Set a one-time tag to be used on the next logging
     *
     * @param tag Tag to be used
     *
     * @return [Log] to be easily used in chain
     */
    @JvmStatic fun tag(tag: String): Log {
        val loggers = loggerArray
        for (logger in loggers) logger.explicitTag.set(tag)
        return this
    }

    internal val emptyLoggerArray = arrayOf<Logger>()
    internal val loggerList = mutableListOf<Logger>()
    internal var loggerArray = emptyLoggerArray

    /**
     * Add new [Logger]
     *
     * @param logger [Logger] to add
     *
     * @throws IllegalArgumentException if [logger] is [Log] itself
     */
    @JvmStatic fun add(logger: Logger) {
        if (logger === this) throw IllegalArgumentException("Can't add Log into itself.")
        synchronized(loggerList) {
            loggerList.add(logger)
            loggerArray = loggerList.toTypedArray()
        }
    }

    /**
     * Remove previously added [Logger]
     *
     * @param logger [Logger] to remove
     *
     * @throws IllegalStateException if [logger] id not added yet
     */
    @JvmStatic fun remove(logger: Logger) {
        synchronized(loggerList) {
            if (!loggerList.remove(logger)) throw IllegalStateException("Logger is not added yet.")
            loggerArray = if (loggerList.isEmpty()) emptyLoggerArray else loggerList.toTypedArray()
        }
    }

    /**
     * Return all added [Logger]
     *
     * @return [List] of added [Logger]
     */
    @JvmStatic fun loggers(): List<Logger> = listOf(*loggerArray)
}