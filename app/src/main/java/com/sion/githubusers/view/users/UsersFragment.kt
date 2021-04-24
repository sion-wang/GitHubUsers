package com.sion.githubusers.view.users

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.sion.githubusers.R
import com.sion.githubusers.model.vo.GithubUser
import com.sion.githubusers.view.base.BaseFragment
import com.sion.githubusers.view.userdetail.UserDetailDialogFragment
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import timber.log.Timber

@KoinApiExtension
class UsersFragment : BaseFragment() {
    private val viewModel: UsersViewModel by viewModels()
    override fun getLayoutId() = R.layout.fragment_users

    private val userFuncItem = UserFuncItem(
        onUserItemClick = { userItem -> goDetail(userItem) }
    )
    private val userAdapter by lazy {
        val adapter = UserAdapter(userFuncItem)
        val loadStateListener = { loadStatus: CombinedLoadStates ->
            when (loadStatus.refresh) {
                is LoadState.Error -> progress_bar.visibility = View.GONE
                is LoadState.Loading -> progress_bar.visibility = View.VISIBLE
                is LoadState.NotLoading -> {
                    progress_bar.visibility = View.GONE
                }
            }
            when (loadStatus.append) {
                is LoadState.Error -> Timber.e("append Error:${(loadStatus.append as LoadState.Error).error.localizedMessage}")
                is LoadState.Loading -> Timber.d("append Loading endOfPaginationReached:${(loadStatus.append as LoadState.Loading).endOfPaginationReached}")
                is LoadState.NotLoading -> Timber.d("append NotLoading endOfPaginationReached:${(loadStatus.append as LoadState.NotLoading).endOfPaginationReached}")
            }
        }
        adapter.addLoadStateListener(loadStateListener)
        adapter
    }

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
                userAdapter.submitData(it)
            }
        }
    }

    private fun goDetail(item: GithubUser) {
        UserDetailDialogFragment(item.id.toString()).show(
            requireActivity().supportFragmentManager,
            UsersFragment::class.java.simpleName
        )
    }
}