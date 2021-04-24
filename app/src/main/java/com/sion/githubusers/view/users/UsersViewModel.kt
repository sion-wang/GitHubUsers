package com.sion.githubusers.view.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sion.githubusers.view.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import timber.log.Timber

@KoinApiExtension
class UsersViewModel : BaseViewModel() {
    fun getUsers() {
        viewModelScope.launch {
            apiRepository.getUsers()
                .catch {
                    Timber.e(it)
                }
                .collect {
                    Timber.d("@@$it")
                }
        }
    }
}