package com.ike.wanandroidc.ui

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.util.Log
import com.ike.wanandroidc.base.BaseActivity
import com.ike.wanandroidc.databinding.ActivityAnimBinding

/**
 * @author ike.Liu
 * @date 2021年05月10日 21:19
 */
class AnimationActivity : BaseActivity<ActivityAnimBinding>() {
    override fun initData() {

        mBinding.btnStartAnimation.setOnClickListener {
            startAnimation()
        }
    }

    private fun startAnimation() {
        val animatorSet=AnimatorSet()
        val scaleAnimY = ValueAnimator()

        scaleAnimY.setFloatValues(1f, 0.20f)
        scaleAnimY.addUpdateListener {
            mBinding.animView.scaleY = it.animatedValue as Float
        }
        val scaleAnimX= ValueAnimator()
        scaleAnimX.setFloatValues(1f, 0.2361111111111111f)
        scaleAnimX.addUpdateListener {
            mBinding.animView.scaleX = it.animatedValue as Float
        }
        val translateAnimY = ValueAnimator()
        val i = mBinding.tempView.top + mBinding.tempView.height / 2
        val j=mBinding.animView.top+mBinding.animView.height/2
        val result=i-j
        Log.e(TAG,"i:${mBinding.tempView.top},j:${mBinding.tempView.height }")
        translateAnimY.setFloatValues(0f, result.toFloat()
        )
        translateAnimY.addUpdateListener {
            mBinding.animView.translationY= it.animatedValue as Float
        }

        val translateAnimX = ValueAnimator()
        val iX = mBinding.tempView.left + mBinding.tempView.width / 2
        val jX=mBinding.animView.left+mBinding.animView.width/2
        val resultX=iX-jX
        translateAnimX.setFloatValues(0f, resultX.toFloat()
        )
        translateAnimX.addUpdateListener {
            mBinding.animView.translationX= it.animatedValue as Float
        }
        //scaleAnim,
        animatorSet.playTogether(scaleAnimX,scaleAnimY,translateAnimY,translateAnimX)
        animatorSet.start()


    }
    companion object{
        const val TAG="AnimationActivity"
    }
}