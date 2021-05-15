package com.sion.githubusers.view.users

import app.cash.turbine.test
import com.sion.githubusers.MainCoroutineRule
import com.sion.githubusers.allRemoteUser
import com.sion.githubusers.model.api.user.FakeUserApiService
import com.sion.githubusers.model.api.user.IUserApiRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
class UsersViewModelTest {

    private lateinit var usersViewModel: UsersViewModel
    private lateinit var fakeUserApiRepository: IUserApiRepository
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Before
    fun createViewModel() {
        val fakeUserApiService = FakeUserApiService(allRemoteUser)
        fakeUserApiRepository = FakeUserApiRepository(fakeUserApiService)
        usersViewModel = UsersViewModel(fakeUserApiRepository)
    }

    @ExperimentalTime
    @Test
    fun testGetUsers() = runBlockingTest {
        val flow = usersViewModel.getUsers()
        flow.test {
            assertThat(this, not(CoreMatchers.nullValue()))
            cancelAndIgnoreRemainingEvents()
        }
    }
}