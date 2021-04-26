package com.sion.githubusers.view.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import retrofit2.HttpException

abstract class BaseDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onStart() {
        super.onStart()
        if (isFullLayout()) {
            val window = dialog?.window
            if (window != null) {
                window.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        } else {
            val window = dialog?.window
            if (window != null) {
                val widthPixels = (resources.displayMetrics.widthPixels * 0.8).toInt()
                window.setLayout(widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT)
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }

    abstract fun isFullLayout(): Boolean

    @LayoutRes
    abstract fun getLayoutId(): Int

    fun showDialog(title: String = "Error", msg: String = "Something went wrong!\nRetry it later.") {
        (requireActivity() as BaseActivity).showDialog(title, msg)
    }
}