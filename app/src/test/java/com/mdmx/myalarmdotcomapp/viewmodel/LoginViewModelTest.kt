package com.mdmx.myalarmdotcomapp.viewmodel


import com.mdmx.myalarmdotcomapp.model.MainRepository
import com.mdmx.myalarmdotcomapp.testutil.TestDispatcherExtension
import com.mdmx.myalarmdotcomapp.testutil.TestResponse
import com.mdmx.myalarmdotcomapp.util.Constant.ERROR
import com.mdmx.myalarmdotcomapp.util.Constant.LOGGEDIN
import com.mdmx.myalarmdotcomapp.util.Constant.LOGGEDIN_FIELD
import com.mdmx.myalarmdotcomapp.util.Constant.LOGIN_OK
import com.mdmx.myalarmdotcomapp.util.Resource
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.mockito.Mockito
import org.mockito.kotlin.mock


class LoginViewModelTest {

    @JvmField
    @RegisterExtension
    val testDispatcherExtension = TestDispatcherExtension()

    private val repository = mock<MainRepository>()
    private val dispatchers = testDispatcherExtension.testDispatchers
    private lateinit var viewModel: LoginViewModel

    @AfterEach
    fun afterEach() {
        Mockito.reset(repository)
    }

    @BeforeEach
    fun beforeEach() {
        this.viewModel = LoginViewModel(repository = repository, dispatchers = dispatchers)

    }

    @Test
    fun `login and password correct`() = runTest {

        val login = "Royal1234!"
        val password = "Royal1234!"
        val testResponse = TestResponse(LOGGEDIN_FIELD, LOGGEDIN)

        Mockito.`when`(repository.login(login = login, password = password))
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
        val testResponse = TestResponse(LOGGEDIN_FIELD, "")

        Mockito.`when`(repository.login(login = login, password = password))
            .thenReturn(Resource.Success(testResponse))

        viewModel.login(login, password)

        val expected = LoginViewModel.LoginEvent.Failure(ERROR).javaClass
        val actual = viewModel.result.value.javaClass

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `login and password empty`() = runTest {

        val login = ""
        val password = ""

        Mockito.`when`(repository.login(login = login, password = password))
            .thenReturn(Resource.Error(ERROR))

        viewModel.login(login, password)

        val expected = LoginViewModel.LoginEvent.Failure(ERROR).javaClass
        val actual = viewModel.result.value.javaClass

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `login empty`() = runTest {

        val login = ""
        val password = "Royal1234!"

        Mockito.`when`(repository.login(login = login, password = password))
            .thenReturn(Resource.Error(ERROR))

        viewModel.login(login, password)

        val expected = LoginViewModel.LoginEvent.Failure(ERROR).javaClass
        val actual = viewModel.result.value.javaClass

        Assertions.assertEquals(expected, actual)

    }

    @Test
    fun `password empty`() = runTest {

        val login = "Royal1234!"
        val password = ""

        Mockito.`when`(repository.login(login = login, password = password))
            .thenReturn(Resource.Error(ERROR))

        viewModel.login(login, password)

        val expected = LoginViewModel.LoginEvent.Failure(ERROR).javaClass
        val actual = viewModel.result.value.javaClass

        Assertions.assertEquals(expected, actual)

    }
}

