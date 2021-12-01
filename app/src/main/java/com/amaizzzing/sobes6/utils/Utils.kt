package com.amaizzzing.sobes6.utils

import android.view.View

fun Int.toBoolean() = this != 0

fun View.setGone() {
    this.visibility = View.GONE
}

fun View.setVisible() {
    this.visibility = View.VISIBLE
}