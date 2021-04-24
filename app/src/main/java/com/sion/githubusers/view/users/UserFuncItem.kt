package com.sion.githubusers.view.users

import com.sion.githubusers.model.vo.GithubUser

data class UserFuncItem (
    val onUserItemClick: ((GithubUser) -> Unit) = { _ -> },
)