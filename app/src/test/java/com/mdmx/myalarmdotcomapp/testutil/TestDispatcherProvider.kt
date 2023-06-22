package com.mdmx.myalarmdotcomapp.testutil

import com.mdmx.myalarmdotcomapp.util.DispatcherProvider
import kotlinx.coroutines.test.TestCoroutineDispatcher

class TestDispatcherProvider(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : DispatcherProvider {
    override val default
        get() = testDispatcher
    override val io
        get() = testDispatcher
    override val main
        get() = testDispatcher
    override val unconfined
        get() = testDispatcher
}

