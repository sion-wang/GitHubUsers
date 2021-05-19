package com.sion.githubusers.view.users

import com.sion.githubusers.model.api.user.IUserApiRepository
import com.sion.githubusers.model.api.user.UserApiService
import com.sion.githubusers.model.vo.GithubUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import retrofit2.Response

class FakeUserApiRepository(private val userApiService: UserApiService) : IUserApiRepository {
    override suspend fun getUsers(since: Int, perPage: Int): Response<List<GithubUser>> {
        return userApiService.getUsers(since, perPage)
    }

    override suspend fun getUserByName(name: String) =
        flowOf(userApiService.getUserByName(name))
            .flowOn(Dispatchers.IO)
            .map {
                if (it.isSuccessful) {
                    return@map it
                } else {
                    throw HttpException(it)
                }
            }
}