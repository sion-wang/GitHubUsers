package com.sion.githubusers.model.api.user

import com.sion.githubusers.model.vo.GithubUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import retrofit2.Response

class UserApiRepository(private val userApiService: UserApiService) : IUserApiRepository {

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

    override suspend fun getUsers(since: Int, perPage: Int): Response<List<GithubUser>> {
        return userApiService.getUsers(since, perPage)
    }

    override suspend fun getUserByName(name: String): Flow<Response<GithubUser>> =
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