package com.ike.wanandroidc.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * Activity基类
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected lateinit var mBinding: VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initData()
    }

    private fun initViewBinding() {
        val type = this::class.java.genericSuperclass as ParameterizedType
        val argClass = type.actualTypeArguments[0] as Class<*>
        val inflateMethod = argClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        mBinding = inflateMethod.invoke(null, layoutInflater) as VB
        setContentView(mBinding.root)
    }

    fun initListener() {

    }

    abstract fun initData();
}