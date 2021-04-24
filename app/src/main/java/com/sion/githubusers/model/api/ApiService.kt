package com.sion.githubusers.model.api

import com.sion.githubusers.model.vo.GithubUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/users")
    suspend fun getUsers(): Response<List<GithubUser>>
}