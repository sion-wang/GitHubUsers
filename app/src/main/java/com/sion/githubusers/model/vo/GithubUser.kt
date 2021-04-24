package com.sion.githubusers.model.vo

data class GithubUser(
    val login: String = "",
    val avatar_url: String = "",
    val site_admin: Boolean = false
)
