package com.sion.githubusers.view.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sion.githubusers.R

abstract class BaseActivity: AppCompatActivity() {
    fun navigateTo(fragment: Fragment, backStack: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()
        val currentFragment: Fragment? = if (supportFragmentManager.fragments.size > 0) {
            supportFragmentManager.fragments[0]
        } else {
            null
        }
        if (backStack && currentFragment != null) {
            transaction.addToBackStack(currentFragment::class.java.simpleName)
        }
        transaction.replace(
            R.id.layout_fragment, fragment, fragment::class.java.simpleName
        ).commit()
    }
}