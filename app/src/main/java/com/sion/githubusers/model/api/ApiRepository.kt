package com.sion.githubusers.model.api

import com.sion.githubusers.model.vo.GithubUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import retrofit2.Response

class ApiRepository(private val apiService: ApiService) {

    fun getUsers(): Flow<Response<List<GithubUser>>> {
        return flow {
            emit(apiService.getUsers())
        }.flowOn(Dispatchers.IO).map {
            if (it.isSuccessful) {
                return@map it
            } else {
                throw HttpException(it)
            }
        }
    }
}