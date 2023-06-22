package com.mdmx.myalarmdotcomapp.testutil

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext


class TestDispatcherExtension(
    val testDispatchers: TestDispatcherProvider = TestDispatcherProvider()
) : BeforeEachCallback, AfterEachCallback,
    TestCoroutineScope by TestCoroutineScope(testDispatchers.testDispatcher) {

    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(testDispatchers.testDispatcher)
    }


    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()
        testDispatchers.testDispatcher.cleanupTestCoroutines()
    }
}