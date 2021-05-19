package com.sion.githubusers.view.users

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.sion.githubusers.R
import com.sion.githubusers.model.api.user.IUserApiRepository
import com.sion.githubusers.model.vo.GithubUser
import com.sion.githubusers.view.base.BaseFragment
import com.sion.githubusers.view.base.footer.BaseLoadStateAdapter
import com.sion.githubusers.view.userdetail.UserDetailDialogFragment
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class UsersFragment : BaseFragment() {
    companion object {
        private const val KEY_REPO = "key.repo"
        fun createBundle(repository: IUserApiRepository): Bundle {
            val bundle = Bundle()
            bundle.putSerializable(KEY_REPO, repository)
            return bundle
        }
    }

    private val viewModel: UsersViewModel by viewModel()
    override fun getLayoutId() = R.layout.fragment_users

    private val userFuncItem = UserFuncItem(
        onUserItemClick = { userItem -> goDetail(userItem) }
    )

    private val loadStateListener = { loadStatus: CombinedLoadStates ->
        when (loadStatus.refresh) {
            is LoadState.Error -> {
                showDialog()
                progress_bar.visibility = View.GONE
            }
            is LoadState.Loading -> progress_bar.visibility = View.VISIBLE
            is LoadState.NotLoading -> {
                progress_bar.visibility = View.GONE
            }
        }
        when (loadStatus.append) {
            is LoadState.Error -> {}
            is LoadState.Loading -> {}
            is LoadState.NotLoading -> {}
        }
    }

    private val userAdapter by lazy {
        val adapter = UserAdapter(userFuncItem)
        adapter.addLoadStateListener(loadStateListener)
        adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getSerializable(KEY_REPO)?.also {
            it as IUserApiRepository
            viewModel.setApiRepository(it)
        }
        if (rv_users.adapter == null) {
            rv_users.adapter = userAdapter.withLoadStateFooter(BaseLoadStateAdapter())
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