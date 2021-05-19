package com.sion.githubusers.view.users

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.sion.githubusers.R
import com.sion.githubusers.allRemoteUser
import com.sion.githubusers.model.api.user.FakeUserApiService
import com.sion.githubusers.model.api.user.IUserApiRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class UsersFragmentTest {
    lateinit var fakeUserApiRepository: IUserApiRepository

    @Before
    fun init() {

        val fakeUserApiService = FakeUserApiService(allRemoteUser)
        fakeUserApiRepository = FakeUserApiRepository(fakeUserApiService)
    }

    @Test
    fun displayedInUi() = runBlockingTest {
        val bundle = UsersFragment.createBundle(fakeUserApiRepository)
        launchFragmentInContainer<UsersFragment>(bundle, R.style.Theme_Githubusers)
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
//        Thread.sleep(2000)
    }
}