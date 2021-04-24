package com.sion.githubusers.view.users

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sion.githubusers.model.vo.GithubUser
import kotlinx.android.synthetic.main.item_user.view.*

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val ivAvatar: ImageView = view.iv_avatar
    private val tvName: TextView = view.tv_name
    private val tvStaff: TextView = view.tv_staff

    fun onBind(item: GithubUser) {
        Glide.with(ivAvatar.context).load(item.avatar_url).circleCrop().into(ivAvatar)
        tvName.text = item.login
        tvStaff.visibility = if (item.site_admin) View.VISIBLE else View.GONE
    }
}