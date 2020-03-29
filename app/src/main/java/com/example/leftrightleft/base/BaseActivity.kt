package com.example.leftrightleft.base

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.leftrightleft.utils.CommonUtils
import java.lang.reflect.ParameterizedType


abstract class BaseActivity<T : ViewModel> : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题栏
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );// 隐藏状态栏
        CommonUtils.hideNavigationBar(this)
        setContentView(setLayoutRes())
        abstractInit()
    }

    val viewModel: T by lazy {
        //获取父类泛型参数的实例
        val t =
            (this.javaClass.genericSuperclass as ParameterizedType).getActualTypeArguments().get(0) as Class<T>
        ViewModelProvider(this).get(t)

    }

    protected abstract fun setLayoutRes(): Int
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initEvent()
    protected abstract fun initObserve()
    private fun abstractInit() {
        initView()
        initEvent()
        initObserve()
        initData()
    }
}