package com.sion.githubusers.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import retrofit2.HttpException

abstract class BaseFragment: Fragment() {
    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    fun showDialog(title: String = "Error", msg: String = "Something went wrong!\nRetry it later.") {
        (requireActivity() as BaseActivity).showDialog(title, msg)
    }
}