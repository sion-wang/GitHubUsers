package com.sion.githubusers.di

import com.sion.githubusers.view.userdetail.UserDetailViewModel
import com.sion.githubusers.view.users.UsersViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { UsersViewModel(get()) }
    viewModel { UserDetailViewModel(get()) }
}