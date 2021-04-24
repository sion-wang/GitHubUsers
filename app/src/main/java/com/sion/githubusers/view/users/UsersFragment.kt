package com.sion.githubusers.view.users

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sion.githubusers.R
import com.sion.githubusers.view.base.BaseFragment
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class UsersFragment: BaseFragment() {
    private val viewModel: UsersViewModel by viewModels()
    override fun getLayoutId() = R.layout.fragment_users

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUsers()
    }
}