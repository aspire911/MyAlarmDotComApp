package com.mdmx.myalarmdotcomapp.viewmodel


import com.mdmx.myalarmdotcomapp.model.alarmdotcomremoterepository.AlarmDotComRemoteDataSource
import com.mdmx.myalarmdotcomapp.model.localpersistentrepository.LocalPersistentDataSource
import com.mdmx.myalarmdotcomapp.testutil.TestDispatcherProvider
import com.mdmx.myalarmdotcomapp.util.Constant.EMPTY_STRING
import com.mdmx.myalarmdotcomapp.util.Constant.ERROR
import com.mdmx.myalarmdotcomapp.util.Constant.LOGGEDIN
import com.mdmx.myalarmdotcomapp.util.Constant.LOGGEDIN_FIELD
import com.mdmx.myalarmdotcomapp.util.Constant.LOGIN_OK
import com.mdmx.myalarmdotcomapp.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.jsoup.Connection
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private val testDispatchers: TestDispatcherProvider = TestDispatcherProvider()
    private val alarmDotComRemoteRepository = mock<AlarmDotComRemoteDataSource>()
    private val localPersistentRepository = mock<LocalPersistentDataSource>()
    private val viewModel = LoginViewModel(
        localPersistentRepository = localPersistentRepository,
        alarmDotComRemoteRepository = alarmDotComRemoteRepository,
        dispatchers = testDispatchers
    )
    private val response = mock<Connection.Response>()


    @Test
    fun `login and password correct`() = runTest {

        val login = "Royal1234!"
        val password = "Royal1234!"
        whenever(response.cookies()).thenReturn(mutableMapOf(LOGGEDIN_FIELD to LOGGEDIN))

        whenever(alarmDotComRemoteRepository.login(login = login, password = password))
            .thenReturn(Resource.Success(response))

        viewModel.login(login, password)

        val expected = LoginEvent.Success(LOGIN_OK).javaClass
        val actual = viewModel.result.value.javaClass

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `login and password incorrect`() = runTest {
        val login = "Royal1234!"
        val password = "Royal1234!"
        whenever(response.cookies()).thenReturn(mutableMapOf(LOGGEDIN_FIELD to EMPTY_STRING))


        whenever(alarmDotComRemoteRepository.login(login = login, password = password))
            .thenReturn(Resource.Success(response))

        viewModel.login(login, password)

        val expected = LoginEvent.Failure(ERROR).javaClass
        val actual = viewModel.result.value.javaClass

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `login and password empty`() = runTest {

        val login = EMPTY_STRING
        val password = EMPTY_STRING

        whenever(alarmDotComRemoteRepository.login(login = login, password = password))
            .thenReturn(Resource.Error(ERROR))

        viewModel.login(login, password)

        val expected = LoginEvent.Failure(ERROR).javaClass
        val actual = viewModel.result.value.javaClass

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `login empty`() = runTest {

        val login = EMPTY_STRING
        val password = "Royal1234!"

        whenever(alarmDotComRemoteRepository.login(login = login, password = password))
            .thenReturn(Resource.Error(ERROR))

        viewModel.login(login, password)

        val expected = LoginEvent.Failure(ERROR).javaClass
        val actual = viewModel.result.value.javaClass

        Assertions.assertEquals(expected, actual)

    }

    @Test
    fun `password empty`() = runTest {

        val login = "Royal1234!"
        val password = EMPTY_STRING

        whenever(alarmDotComRemoteRepository.login(login = login, password = password))
            .thenReturn(Resource.Error(ERROR))

        viewModel.login(login, password)

        val expected = LoginEvent.Failure(ERROR).javaClass
        val actual = viewModel.result.value.javaClass

        Assertions.assertEquals(expected, actual)

    }
}

