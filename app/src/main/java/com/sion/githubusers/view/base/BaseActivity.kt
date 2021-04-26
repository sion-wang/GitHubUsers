package com.sion.githubusers.view.base

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sion.githubusers.R

abstract class BaseActivity : AppCompatActivity() {
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

    lateinit var alertDialog: AlertDialog

    fun showDialog(title: String, msg: String) {
        if (!::alertDialog.isInitialized) {
            alertDialog =
                AlertDialog.Builder(this).setPositiveButton(R.string.ok, null).setTitle(title)
                    .setMessage(msg).create()
        }
        alertDialog.takeIf { !it.isShowing }?.run { this.dismiss() }
        alertDialog.show()
    }
}