package com.sion.githubusers.model.api.user

import com.sion.githubusers.model.vo.GithubUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {
    @GET("/users")
    suspend fun getUsers(
        @Query("since") since: Int = 0,
        @Query("per_page") per_page: Int = 20
    ): Response<List<GithubUser>>

    @GET("/user/{username}")
    suspend fun getUserByName(
        @Path("username") username: String = "",
    ): Response<GithubUser>
}