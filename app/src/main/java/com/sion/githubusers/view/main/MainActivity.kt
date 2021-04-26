package com.sion.githubusers.view.main

import android.os.Bundle
import com.sion.githubusers.R
import com.sion.githubusers.view.base.BaseActivity
import com.sion.githubusers.view.users.UsersFragment
import org.koin.core.component.KoinApiExtension

class MainActivity : BaseActivity() {
    @KoinApiExtension
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateTo(UsersFragment())
    }
}