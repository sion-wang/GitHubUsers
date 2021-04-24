package com.sion.githubusers.view.users

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sion.githubusers.R
import com.sion.githubusers.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class UsersFragment : BaseFragment() {
    private val viewModel: UsersViewModel by viewModels()
    override fun getLayoutId() = R.layout.fragment_users

    private val userAdapter by lazy { UserAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (rv_users.adapter == null) {
            rv_users.adapter = userAdapter
            getUsers()
        }
    }

    private fun getUsers() {
        lifecycleScope.launch {
            viewModel.getUsers().collectLatest {
                (rv_users.adapter as UserAdapter).submitData(it)
            }
        }
    }
}