package com.mdmx.myalarmdotcomapp.viewmodel


import com.mdmx.myalarmdotcomapp.model.MainRepository
import com.mdmx.myalarmdotcomapp.util.Constant.ERROR
import com.mdmx.myalarmdotcomapp.util.Constant.LOGIN_OK
import com.mdmx.myalarmdotcomapp.util.DispatcherProvider
import com.mdmx.myalarmdotcomapp.util.Resource
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock


class LoginViewModelTest {

    private val repository = mock<MainRepository>()
    private val dispatchers = mock<DispatcherProvider>()
    private lateinit var viewModel: LoginViewModel

    @AfterEach
    fun afterEach() {
        Mockito.reset(repository)
        Mockito.reset(dispatchers)
    }

    @BeforeEach
    fun beforeEach() {
        this.viewModel = LoginViewModel(repository = repository, dispatchers = dispatchers)
    }

    @Test
    fun `login and password correct`() = runTest {

        val login = "Royal1234!"
        val password = "Royal1234!"


        Mockito.`when`(repository.login(login = login, password = password))
            .thenReturn(Resource.Error(ERROR))

        viewModel.login(login, password)

        val expected = LoginViewModel.LoginEvent.Failure(ERROR)
        val actual = viewModel.result.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `login and password incorrect`() = runTest {
        val login = "Royal1234!"
        val password = "Royal1234!"



        viewModel.login(login, password)

        val expected = LoginViewModel.LoginEvent.Success(LOGIN_OK)
        val actual = viewModel.result.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `login and password empty`() = runTest {

        val login = ""
        val password = ""

        Mockito.`when`(repository.login(login = login, password = password))
            .thenReturn(Resource.Error(ERROR))

        viewModel.login(login, password)

        val expected = LoginViewModel.LoginEvent.Failure(ERROR)
        val actual = viewModel.result.value

        Assertions.assertEquals(expected, actual)

    }

    @Test
    fun `login empty`() = runTest {

        val login = ""
        val password = "Royal1234!"

        Mockito.`when`(repository.login(login = login, password = password))
            .thenReturn(Resource.Error(ERROR))

        viewModel.login(login, password)

        val expected = LoginViewModel.LoginEvent.Failure(ERROR)
        val actual = viewModel.result.value

        Assertions.assertEquals(expected, actual)

    }

    @Test
    fun `password empty`() = runTest {

        val login = "Royal1234!"
        val password = ""

        Mockito.`when`(repository.login(login = login, password = password))
            .thenReturn(Resource.Error(ERROR))

        viewModel.login(login, password)

        val expected = LoginViewModel.LoginEvent.Failure(ERROR)
        val actual = viewModel.result.value

        Assertions.assertEquals(expected, actual)

    }
}

