package com.sion.githubusers.view.userdetail

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.sion.githubusers.R
import com.sion.githubusers.model.api.ApiResult
import com.sion.githubusers.model.vo.GithubUser
import com.sion.githubusers.view.base.BaseDialogFragment
import kotlinx.android.synthetic.main.fragment_user_detail.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class UserDetailDialogFragment(val name: String) : BaseDialogFragment() {

    private val viewModel: UserDetailViewModel by viewModel()

    override fun isFullLayout() = true

    override fun getLayoutId() = R.layout.fragment_user_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ib_close.setOnClickListener { dismiss() }

        viewModel.getUser.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Loaded -> progress_bar.visibility = View.GONE
                is ApiResult.Loading -> progress_bar.visibility = View.VISIBLE
                is ApiResult.Error -> { showDialog() }
                is ApiResult.Success -> it.result?.run { setupUI(this) }
            }
        }

        progress_bar.visibility = View.VISIBLE
        viewModel.getUser(name)
    }

    private fun setupUI(item: GithubUser) {
        Glide.with(requireContext()).load(item.avatar_url).placeholder(R.drawable.user).circleCrop()
            .into(iv_avatar)
        tv_name.text = item.name
        tv_bio.text = item.bio
        tv_login.text = item.login
        tv_staff.visibility = if (item.site_admin) View.VISIBLE else View.GONE
        tv_location.text = item.location
        tv_blog.text = item.blog
    }

}