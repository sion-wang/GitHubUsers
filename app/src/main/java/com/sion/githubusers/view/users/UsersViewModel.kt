package com.sion.githubusers.view.users

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sion.githubusers.model.api.user.IUserApiRepository
import com.sion.githubusers.model.api.user.UserApiRepository
import com.sion.githubusers.model.vo.GithubUser
import com.sion.githubusers.view.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class UsersViewModel(private val userApiRepository: IUserApiRepository) : BaseViewModel() {
    fun getUsers(): Flow<PagingData<GithubUser>> {
        return Pager(
            config = PagingConfig(
                pageSize = UserApiRepository.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserPagingSource(userApiRepository) }
        ).flow.cachedIn(viewModelScope)
    }
}