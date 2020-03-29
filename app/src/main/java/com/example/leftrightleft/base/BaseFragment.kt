package com.example.leftrightleft.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType


abstract class BaseFragment<T : ViewModel> : Fragment() {

    var enableAutoEventBus: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(setLayoutRes(), container, false)
    }
    val viewModel:T by lazy {
        //获取父类泛型参数的实例
        val t = (this.javaClass.genericSuperclass as ParameterizedType).getActualTypeArguments().get(0) as Class<T>
        ViewModelProvider(this).get(t)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        abstractInit()
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




    override fun onDetach() {
        super.onDetach()
    }
}