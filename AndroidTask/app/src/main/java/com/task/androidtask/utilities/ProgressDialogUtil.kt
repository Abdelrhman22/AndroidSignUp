package com.task.androidtask.utilities


import android.content.Context
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog


object ProgressDialogUtil {

    private var dialog: AlertDialog? = null

    fun showProgressDialog(context: Context, title: String = "Loading ...") {
        dismissProgressDialog()
        val progressBar = ProgressBar(context)
        dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setView(progressBar)
            .setCancelable(false)
            .create()
        dialog?.show()
    }

    fun dismissProgressDialog() {
        dialog?.dismiss()
        dialog = null
    }
}

