package com.sion.githubusers.model.api.user

import com.sion.githubusers.model.vo.GithubUser
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.io.Serializable

interface IUserApiRepository: Serializable {
    suspend fun getUsers(since: Int, perPage: Int = UserApiRepository.NETWORK_PAGE_SIZE): Response<List<GithubUser>>

    suspend fun getUserByName(name: String): Flow<Response<GithubUser>>
}