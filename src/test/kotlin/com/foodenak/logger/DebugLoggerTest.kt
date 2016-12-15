package com.foodenak.logger

import junit.framework.TestCase

class DebugLoggerTest : TestCase() {

    private var tag: String? = null

    override fun setUp() {
        Log.add(object : DebugLogger() {
            override fun println(priority: Priority, tag: String?, message: String) {
                this@DebugLoggerTest.tag = tag
            }
        })
    }

    fun testTag() {
        val expectedTag = javaClass.simpleName //expected tag is the caller class name

        //do logging
        Log.d(message = "Test!")

        assertEquals(expectedTag, tag)
    }

    fun testExplicitTag() {
        val expectedTag = "ExpectedTag" //expected tag is anything you set

        //set explicit tag
        Log.tag(expectedTag)
        //do logging
        Log.d(message = "Test!")

        assertEquals(expectedTag, tag)
    }
}