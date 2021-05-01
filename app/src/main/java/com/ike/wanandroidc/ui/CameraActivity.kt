package com.ike.wanandroidc.ui

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
    }

    companion object {
        const val TAG = "CameraActivity"
    }
}