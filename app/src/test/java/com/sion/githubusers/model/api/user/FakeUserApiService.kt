package com.sion.githubusers.model.api.user

import com.sion.githubusers.model.vo.GithubUser
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeUserApiService(private val data: List<GithubUser>? = arrayListOf()) : UserApiService {
    override suspend fun getUsers(since: Int, per_page: Int): Response<List<GithubUser>> {
        data?.let {
            val end = if (since + per_page > it.size) it.size else (since + per_page)
            it.subList(since, end).run {
                return Response.success(this)
            }
        }
        return Response.error(
            500, "{\"msg\":\"null response\"}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
    }

    override suspend fun getUserByName(username: String): Response<GithubUser> {
        var result: GithubUser? = null
        data?.forEach ff@{
            if (it.name == username) {
                result = it
                return@ff
            }
        }
        return if (result == null) {
            Response.error(
                500, "{\"msg\":\"not found\"}"
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
        } else {
            Response.success(result)
        }
    }
}