package com.ike.wanandroidc.base

import android.content.Context
import android.content.ContextWrapper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

fun Context?.getLifeCycle(): Lifecycle? {
    var context: Context? = this
    while (true) {
        when (context) {
            is LifecycleOwner -> return context.lifecycle
            !is ContextWrapper -> return null
            else -> context = context.baseContext
        }
    }
}

fun Context?.getLifeCycleOwner(): LifecycleOwner? {
    var context: Context? = this
    while (true) {
        when (context) {
            is LifecycleOwner -> return context
            !is ContextWrapper -> return null
            else -> context = context.baseContext
        }
    }
}