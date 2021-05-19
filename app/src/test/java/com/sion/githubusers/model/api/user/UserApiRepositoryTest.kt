package com.sion.githubusers.model.api.user

import com.sion.githubusers.allRemoteUser
import com.sion.githubusers.user2
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class UserApiRepositoryTest {
    private lateinit var fakeUserApiService: UserApiService
    private lateinit var userApiRepository: UserApiRepository

    @Before
    fun createRepository() {
        fakeUserApiService = FakeUserApiService(allRemoteUser)
        userApiRepository = UserApiRepository(fakeUserApiService)
    }

    @Test
    fun testGetUsers_request0to5() = runBlockingTest {
        val response = userApiRepository.getUsers(0, 5)
        val result = response.body()
        val expected = allRemoteUser.subList(0, 5)

        assertThat(result, IsEqual(expected))
    }

    @Test
    fun testGetUsers_request0toEnd() = runBlockingTest {
        val response = userApiRepository.getUsers(8, 5)
        val result = response.body()
        val expected = allRemoteUser.subList(8, 13)

        assertThat(result, IsEqual(expected))
    }

    @Test
    fun testGetUserByName() = runBlockingTest {
        userApiRepository.getUserByName("Andy").collect {
            val result = it.body()
            val expected = user2
            assertThat(result, IsEqual(expected))
        }
    }

    @Test
    fun testGetUserByName_nullResult() = runBlockingTest {
        userApiRepository.getUserByName("AAAAA").catch { error ->
            val result = (error as HttpException).code()
            val expected = 500
            assertThat(result, IsEqual(expected))
        }.collect()
    }
}