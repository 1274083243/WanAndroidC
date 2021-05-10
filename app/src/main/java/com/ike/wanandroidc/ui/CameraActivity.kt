package com.ike.wanandroidc.ui

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.util.Log
import androidx.activity.viewModels
import androidx.camera.view.PreviewView
import androidx.lifecycle.lifecycleScope
import com.ike.wanandroidc.CameraViewModel
import com.ike.wanandroidc.base.BaseActivity
import com.ike.wanandroidc.base.ext.awaitStart
import com.ike.wanandroidc.databinding.ActivityCameraBinding
import kotlinx.coroutines.launch

/**
 * 相机预览界面
 */
class CameraActivity : BaseActivity<ActivityCameraBinding>() {
    private val mCameraViewModel: CameraViewModel by viewModels()
    private var isRecover = false
    override fun initData() {
        lifecycleScope.launch {
            lifecycle.awaitStart()
            Log.d(TAG, "mBinding.cameraDisplay:${mBinding.cameraDisplay}")
            mBinding.cameraDisplay.implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            mBinding.cameraDisplay.post {
                mCameraViewModel.bindCamera(
                    this@CameraActivity,
                    mBinding.cameraDisplay.display,
                    mBinding.cameraDisplay.surfaceProvider
                )
            }
        }
        mBinding.btnStartAnimation.setOnClickListener {
            if (isRecover) {
                mBinding.btnStartAnimation.text = "恢复"
            } else {
                mBinding.btnStartAnimation.text = "缩小"
            }
            isRecover = !isRecover
            startAnimation(isRecover)
        }
    }

    companion object {
        const val TAG = "CameraActivity"
    }

    private fun startAnimation(isRecover: Boolean) {
        val animatorSet = AnimatorSet()
        val scaleAnimY = ValueAnimator()
        if (isRecover) {
            scaleAnimY.setFloatValues(0.20f, 1f)
        } else {
            scaleAnimY.setFloatValues(1f, 0.20f)

        }
        scaleAnimY.addUpdateListener {
            mBinding.cameraDisplay.scaleY = it.animatedValue as Float
        }
        val scaleAnimX = ValueAnimator()
        if (isRecover) {
            scaleAnimX.setFloatValues(0.2361111111111111f, 1f)
        } else {
            scaleAnimX.setFloatValues(1f, 0.2361111111111111f)

        }
        scaleAnimX.addUpdateListener {
            mBinding.cameraDisplay.scaleX = it.animatedValue as Float
        }
        val translateAnimY = ValueAnimator()
        val i = mBinding.tempView.top + mBinding.tempView.height / 2
        val j = mBinding.cameraDisplay.top + mBinding.cameraDisplay.height / 2
        val result = i - j
        Log.e(AnimationActivity.TAG, "i:${mBinding.tempView.top},j:${mBinding.tempView.height}")
        if (isRecover) {
            translateAnimY.setFloatValues(result.toFloat(), 0f)
        } else {
            translateAnimY.setFloatValues(0f, result.toFloat())
        }

        translateAnimY.addUpdateListener {
            mBinding.cameraDisplay.translationY = it.animatedValue as Float
        }

        val translateAnimX = ValueAnimator()
        val iX = mBinding.tempView.left + mBinding.tempView.width / 2
        val jX = mBinding.cameraDisplay.left + mBinding.cameraDisplay.width / 2
        val resultX = iX - jX
        if (isRecover) {
            translateAnimX.setFloatValues(resultX.toFloat(), 0f)
        } else {
            translateAnimX.setFloatValues(0f, resultX.toFloat())

        }
        translateAnimX.addUpdateListener {
            mBinding.cameraDisplay.translationX = it.animatedValue as Float
        }
        //scaleAnim,
        animatorSet.playTogether(scaleAnimX, scaleAnimY, translateAnimY, translateAnimX)
        animatorSet.start()


    }
}