package com.sion.githubusers.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sion.githubusers.R
import com.sion.githubusers.view.base.BaseActivity
import com.sion.githubusers.view.users.UsersFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateTo(UsersFragment())
    }
}