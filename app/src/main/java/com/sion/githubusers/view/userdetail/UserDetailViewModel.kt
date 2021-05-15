package com.sion.githubusers.view.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sion.githubusers.model.api.user.UserApiRepository
import com.sion.githubusers.model.api.ApiResult
import com.sion.githubusers.model.api.user.IUserApiRepository
import com.sion.githubusers.model.vo.GithubUser
import com.sion.githubusers.view.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class UserDetailViewModel(private val userApiRepository: IUserApiRepository) : BaseViewModel() {

    private val _getUser = MutableLiveData<ApiResult<GithubUser>>()
    val getUser: LiveData<ApiResult<GithubUser>> = _getUser

    fun getUser(name: String) {
        viewModelScope.launch {
            userApiRepository.getUserByName(name)
                .onStart { _getUser.value = ApiResult.loading() }
                .onCompletion { _getUser.value = ApiResult.loaded() }
                .catch { _getUser.value = ApiResult.error(it) }
                .collect {
                    _getUser.value = ApiResult.success(it.body())
                }
        }
    }
}