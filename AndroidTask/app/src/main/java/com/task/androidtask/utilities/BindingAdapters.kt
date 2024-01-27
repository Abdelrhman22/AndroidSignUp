package com.task.androidtask.utilities

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleGone")
fun bindViewsVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}