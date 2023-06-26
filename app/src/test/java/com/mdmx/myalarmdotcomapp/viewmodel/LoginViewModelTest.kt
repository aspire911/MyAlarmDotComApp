package com.mdmx.myalarmdotcomapp.viewmodel


import com.mdmx.myalarmdotcomapp.model.alarmdotcomremoterepository.AlarmDotComRemoteDataSource
import com.mdmx.myalarmdotcomapp.model.localpersistentrepository.LocalPersistentDataSource
import com.mdmx.myalarmdotcomapp.testutil.TestDispatcherProvider
import com.mdmx.myalarmdotcomapp.testutil.TestResponse
import com.mdmx.myalarmdotcomapp.util.Constant.EMPTY_STRING
import com.mdmx.myalarmdotcomapp.util.Constant.ERROR
import com.mdmx.myalarmdotcomapp.util.Constant.LOGGEDIN
import com.mdmx.myalarmdotcomapp.util.Constant.LOGGEDIN_FIELD
import com.mdmx.myalarmdotcomapp.util.Constant.LOGIN_OK
import com.mdmx.myalarmdotcomapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private val testDispatchers: TestDispatcherProvider = TestDispatcherProvider()
    private val alarmDotComRemoteRepository = mock<AlarmDotComRemoteDataSource>()
    private val localPersistentRepository = mock<LocalPersistentDataSource>()


    private lateinit var viewModel: LoginViewModel

    @AfterEach
    fun afterEach() {
        Mockito.reset(alarmDotComRemoteRepository)
        Mockito.reset(localPersistentRepository)
        Dispatchers.resetMain()
    }

    @BeforeEach
    fun beforeEach() {
        this.viewModel = LoginViewModel(localPersistentRepository = localPersistentRepository, alarmDotComRemoteRepository = alarmDotComRemoteRepository, dispatchers = testDispatchers)
        Dispatchers.setMain(testDispatchers.testDispatcher)
    }

    @Test
    fun `login and password correct`() = runTest {

        val login = "Royal1234!"
        val password = "Royal1234!"
        val testResponse = TestResponse(LOGGEDIN_FIELD, LOGGEDIN)

        whenever(alarmDotComRemoteRepository.login(login = login, password = password))
            .thenReturn(Resource.Success(testResponse))

        viewModel.login(login, password)

        val expected = LoginViewModel.LoginEvent.Success(LOGIN_OK).javaClass
        val actual = viewModel.result.value.javaClass

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `login and password incorrect`() = runTest {
        val login = "Royal1234!"
        val password = "Royal1234!"
        val testResponse = TestResponse(LOGGEDIN_FIELD, EMPTY_STRING)

        whenever(alarmDotComRemoteRepository.login(login = login, password = password))
            .thenReturn(Resource.Success(testResponse))

        viewModel.login(login, password)

        val expected = LoginViewModel.LoginEvent.Failure(ERROR).javaClass
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

        val expected = LoginViewModel.LoginEvent.Failure(ERROR).javaClass
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

        val expected = LoginViewModel.LoginEvent.Failure(ERROR).javaClass
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

        val expected = LoginViewModel.LoginEvent.Failure(ERROR).javaClass
        val actual = viewModel.result.value.javaClass

        Assertions.assertEquals(expected, actual)

    }
}

