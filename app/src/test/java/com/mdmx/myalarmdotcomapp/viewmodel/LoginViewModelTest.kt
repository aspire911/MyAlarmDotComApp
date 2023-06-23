package com.mdmx.myalarmdotcomapp.viewmodel


import com.mdmx.myalarmdotcomapp.model.apirepository.ApiRepository
import com.mdmx.myalarmdotcomapp.model.sprepository.SpRepository
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

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private val testDispatchers: TestDispatcherProvider = TestDispatcherProvider()
    private val apiRepository = mock<ApiRepository>()
    private val spRepository = mock<SpRepository>()


    private lateinit var viewModel: LoginViewModel

    @AfterEach
    fun afterEach() {
        Mockito.reset(apiRepository)
        Mockito.reset(spRepository)
        Dispatchers.resetMain()
    }

    @BeforeEach
    fun beforeEach() {
        this.viewModel = LoginViewModel(spRepository = spRepository, apiRepository = apiRepository, dispatchers = testDispatchers)
        Dispatchers.setMain(testDispatchers.testDispatcher)
    }

    @Test
    fun `login and password correct`() = runTest {

        val login = "Royal1234!"
        val password = "Royal1234!"
        val testResponse = TestResponse(LOGGEDIN_FIELD, LOGGEDIN)

        Mockito.`when`(apiRepository.login(login = login, password = password))
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

        Mockito.`when`(apiRepository.login(login = login, password = password))
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

        Mockito.`when`(apiRepository.login(login = login, password = password))
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

        Mockito.`when`(apiRepository.login(login = login, password = password))
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

        Mockito.`when`(apiRepository.login(login = login, password = password))
            .thenReturn(Resource.Error(ERROR))

        viewModel.login(login, password)

        val expected = LoginViewModel.LoginEvent.Failure(ERROR).javaClass
        val actual = viewModel.result.value.javaClass

        Assertions.assertEquals(expected, actual)

    }
}

