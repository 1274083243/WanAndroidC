package com.ike.wanandroidc

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.view.Display
import androidx.camera.core.AspectRatio
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.ike.wanandroidc.base.getLifeCycle
import com.ike.wanandroidc.base.getLifeCycleOwner
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * 相机逻辑的ViewModel
 */
class CameraViewModel : ViewModel() {
    private var mCameraProvider: ProcessCameraProvider? = null
    private var preview: Preview? = null
    private var camera: Camera? = null
    private val mResolution = Size(1280, 720)

    private var mCameraId: Int = CameraSelector.LENS_FACING_BACK


    fun bindCamera(
        context: Context,
        display: Display,
        surfaceProvider: Preview.SurfaceProvider
    ) {
        val lifeCycleOwner = context.getLifeCycleOwner() ?: return
        val providerFuture = ProcessCameraProvider.getInstance(context)
        providerFuture.addListener({
            mCameraProvider = providerFuture.get()
            mCameraId = when {
                hasBackCamera() -> CameraSelector.LENS_FACING_BACK
                hasFrontCamera() -> CameraSelector.LENS_FACING_FRONT
                else -> throw IllegalStateException("Back and front camera are unavailable")
            }
            val metrics = DisplayMetrics().also { display.getRealMetrics(it) }
            val rotation = display.rotation
            val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)
            val cameraSelector = CameraSelector.Builder().requireLensFacing(mCameraId).build()
            preview = Preview.Builder()
                .setTargetAspectRatio(screenAspectRatio)
//                .setTargetResolution(mResolution)
                .setTargetRotation(rotation)
                .build()
            mCameraProvider?.unbindAll()


            try {
                camera = mCameraProvider?.bindToLifecycle(
                    lifeCycleOwner, cameraSelector, preview
                )
                preview?.setSurfaceProvider(surfaceProvider)
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(context))

    }

    private fun hasBackCamera(): Boolean {
        val hasCamera = mCameraProvider?.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)
        Log.d(TAG,"hasCamera:$hasCamera")
        return hasCamera ?: false
    }

    private fun hasFrontCamera(): Boolean {
        return mCameraProvider?.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) ?: false
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    companion object {
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
        const val TAG = "CameraViewModel"
    }


}