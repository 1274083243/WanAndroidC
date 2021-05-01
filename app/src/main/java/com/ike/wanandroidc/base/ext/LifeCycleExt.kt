package com.ike.wanandroidc.base.ext

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

 suspend fun Lifecycle.awaitStart() {
    if (currentState.isAtLeast(Lifecycle.State.STARTED)) {
        return
    }
    var lifecycleObserver: LifecycleObserver? = null
    try {
        suspendCancellableCoroutine<Unit> {
            lifecycleObserver = LifecycleEventObserver { source, event ->
                if (event == Lifecycle.Event.ON_START) {
                    it.resume(Unit)
                }
            }
            addObserver(lifecycleObserver!!)
        }
    } finally {
        lifecycleObserver?.let {
            removeObserver(it)
        }
    }
}