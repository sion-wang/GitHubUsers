package com.sion.githubusers.view.userdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sion.githubusers.model.api.user.FakeUserApiService
import com.sion.githubusers.model.api.user.IUserApiRepository
import com.sion.githubusers.*
import com.sion.githubusers.view.users.FakeUserApiRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserDetailViewModelTest {
    private lateinit var userDetailViewModel: UserDetailViewModel
    private lateinit var fakeUserApiRepository: IUserApiRepository

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Before
    fun createViewModel() {
        val fakeUserApiService = FakeUserApiService(allRemoteUser)
        fakeUserApiRepository = FakeUserApiRepository(fakeUserApiService)
        userDetailViewModel = UserDetailViewModel(fakeUserApiRepository)
    }

    @Test
    fun testGetUser() {
        userDetailViewModel.getUser("Andy")
        val value = userDetailViewModel.getUser.getOrAwaitValue()
        assertThat(value, not(CoreMatchers.nullValue()))
    }

    @Test
    fun testGetUser_null() {
        userDetailViewModel.getUser("AAA")
        val value = userDetailViewModel.getUser.getOrAwaitValue()
        assertThat(value, not(CoreMatchers.nullValue()))
    }
}