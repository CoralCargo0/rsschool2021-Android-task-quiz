package com.rsschool.quiz


import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.rsschool.quiz.MainActivity

fun Fragment.hideKeyboard() = view?.let { activity?.hideKeyboard(it) }

fun Activity.hideKeyboard() = hideKeyboard(currentFocus ?: View(this))

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Any?.isNull() = this == null
