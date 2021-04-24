package com.sion.githubusers.model.api

import com.sion.githubusers.model.vo.GithubUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import retrofit2.Response

class ApiRepository(private val apiService: ApiService) {

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

    suspend fun getUsers(since: Int, perPage: Int = NETWORK_PAGE_SIZE): Response<List<GithubUser>> {
        return apiService.getUsers(since, perPage)
    }

    suspend fun getUserByName(name: String): Flow<Response<GithubUser>> =
        flowOf(apiService.getUserByName(name))
            .flowOn(Dispatchers.IO)
            .map {
                if (it.isSuccessful) {
                    return@map it
                } else {
                    throw HttpException(it)
                }
            }
}