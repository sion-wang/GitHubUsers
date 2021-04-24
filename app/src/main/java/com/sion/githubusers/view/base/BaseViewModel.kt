package com.sion.githubusers.view.base

import androidx.lifecycle.ViewModel
import com.sion.githubusers.model.api.ApiRepository
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
abstract class BaseViewModel: ViewModel(), KoinComponent {
    val apiRepository: ApiRepository by inject()
}