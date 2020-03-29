package com.example.leftrightleft

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Message
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.leftrightleft.base.*
import com.example.leftrightleft.fragment.CallFragment
import com.example.leftrightleft.fragment.CameraFragment
import com.example.leftrightleft.fragment.VideoFragment
import com.example.leftrightleft.fragment.WraningFragment
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : BaseActivity<ViewModel>() {
    override fun setLayoutRes(): Int = R.layout.activity_main2
    private val cameraFragment =
        CameraFragment()
    private val callFragment = CallFragment()
    private val wraningFragment =
        WraningFragment()
    private val videoFragment = VideoFragment()
    override fun initView() {

        if (Build.VERSION.SDK_INT >= 23) {
            val REQUEST_CODE_CONTACT = 101
            val permissions =
                arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            //验证是否许可权限
            for (str in permissions) {
                if (checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) { //申请权限
                    requestPermissions(permissions, REQUEST_CODE_CONTACT)
                }
            }
        }
    }

    val handler = Handler(object : Handler.Callback {
        override fun handleMessage(msg: Message): Boolean {
            val fragment = msg.obj as Fragment
            changeFragment(fragment)
            return false
        }

    })

    override fun initData() {
        //解开这两个 就能预览功能  不走循环请求
        rg.visibility = View.VISIBLE

        when (rg.visibility) {
            View.VISIBLE -> {
                rb_main.isChecked = true
            }
            else -> {
                //初始化指定页面
                handler.sendMessage(handler.obtainMessage(0, cameraFragment))
                //开始循环请求
                startLoop()
            }
        }

    }

    override fun initEvent() {
        rg.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_main -> {
                    changeFragment(cameraFragment)
                }
                R.id.rb_call -> {
                    changeFragment(callFragment)
                }
                R.id.rb_msg -> {
                    changeFragment(wraningFragment)
                }
                R.id.rb_video -> {
                    changeFragment(videoFragment)
                }
            }
        }
    }

    fun changeFragment(fragment: Fragment) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.replace(R.id.fl_content, fragment)
        beginTransaction.commit()


    }

    override fun initObserve() {

    }

    fun request() {
        //这里放你们的请求


        //根据你们的规则切换到指定页面
//
//        if(xxx){
//            handler.sendMessage(handler.obtainMessage(0,cameraFragment))
//        }else if(xxx){
//            handler.sendMessage(handler.obtainMessage(0,callFragment))
//        }else if(xxx){
//            handler.sendMessage(handler.obtainMessage(0,wraningFragment))
//        }else if(xxx){
//            handler.sendMessage(handler.obtainMessage(0,videoFragment))
//        }
    }

    fun startLoop() {
        lifecycleScope.launch {
            //每各2秒 循环请求
            delay(2000)
            request()
        }
    }
}