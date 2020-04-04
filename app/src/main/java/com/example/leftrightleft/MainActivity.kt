package com.example.leftrightleft

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.leftrightleft.base.BaseActivity
import com.example.leftrightleft.fragment.CallFragment
import com.example.leftrightleft.fragment.CameraFragment
import com.example.leftrightleft.fragment.VideoFragment
import com.example.leftrightleft.fragment.WraningFragment
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpResponseException
import org.ksoap2.transport.HttpTransportSE
import java.io.File


class MainActivity : BaseActivity<ViewModel>() {
    val INTERVAL = 2000;//请求间隔
    val ENDPOINT = "http://134.175.133.43:8080/WebService.asmx";


    override fun setLayoutRes(): Int = R.layout.activity_main2
    private val cameraFragment =
        CameraFragment()
    private val callFragment = CallFragment()
    private val wraningFragment =
        WraningFragment()
    private val videoFragment = VideoFragment()
    val REQUEST_CODE_CONTACT = 101
    private var currentFragment: Fragment? = null
    override fun initView() {

    }

    val handler = Handler(object : Handler.Callback {
        override fun handleMessage(msg: Message): Boolean {
            val fragment = msg.obj as Fragment
            changeFragment(fragment)
            return false
        }

    })

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_CONTACT) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init()
            }
        }
    }

    override fun initData() {
        val pm = getPackageManager();
        //验证有没有权限
        val permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, packageName));
        //有权限
        if (permission) {
            init()
        } else {
            //没权限 就去申请
            if (Build.VERSION.SDK_INT >= 23) {
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
        if (currentFragment == fragment) {
            return
        }
        currentFragment = fragment
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.replace(R.id.fl_content, fragment)
        beginTransaction.commit()

    }

    override fun initObserve() {

    }

    fun request() {
        try {
            var _result1 = GetNodeStatus()
            val result2 = _result1.getProperty(0) as SoapObject

            if (result2.propertyCount != 0) {
//                val result = arrayOfNulls<String>(result2.propertyCount)
                val soapObject = result2.getProperty(0) as SoapObject
                val property = soapObject.getProperty("MyName").toString()
                when (property) {
                    "Camera" -> {
                        handler.sendMessage(handler.obtainMessage(0, cameraFragment))
                    }
                    "Call" -> {
                        handler.sendMessage(handler.obtainMessage(0, callFragment))
                    }
                    "NoSighal" -> {
                        handler.sendMessage(handler.obtainMessage(0, wraningFragment))
                    }
                    "Video" -> {
                        handler.sendMessage(handler.obtainMessage(0, videoFragment))
                    }
                }
            } else {
                //清空列表
                val msg = Message()
                msg.what = 0
//                handler.sendMessage(msg)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun init() {
        makeDirection()
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

    fun startLoop() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                while (true) {
                    //每各2秒 循环请求
                    delay(2000)
                    request()
                }
            }
        }
    }

    //网络请求，查看机关的触发列表
    fun GetNodeStatus(): SoapObject {
        // 命名空间
        val nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        val methodName = "GetNodeStatus";
        // EndPoint

        // SOAP Action
        val soapAction = "http://tempuri.org/GetNodeStatus";

        // 指定WebService的命名空间和调用的方法名
        val rpc = SoapObject(nameSpace, methodName);

        //rpc.addProperty("room", num);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        val transport = HttpTransportSE(ENDPOINT);

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (e: HttpResponseException) {
            e.printStackTrace();
            throw   Exception();
        }


//        val answer = envelope.bodyIn as (SoapObject);
//        SoapObject answer2 = (SoapObject)answer.getProperty(0);
//        SoapObject answer3 = (SoapObject)answer2.getProperty(0);
//        SoapObject answer4 = (SoapObject)answer3.getProperty(0);
//        SoapObject answer5 = (SoapObject)answer4.getProperty(0);
//        SoapObject answer6 = (SoapObject)answer5.getProperty(0);
//        Toast.makeText(getApplicationContext(),"打印"+answer.getProperty(0).toString(),Toast.LENGTH_LONG);z
//        Log.d("SoapObject", "sdfsdfsef" + answer.getProperty(0).toString())
        return envelope.bodyIn as SoapObject;
    }

    fun makeDirection() {
        val externalStorageState = Environment.getExternalStorageDirectory().absolutePath

        val file = File(externalStorageState + "/zuoyouzuo")
        if (!file.exists()) {
            try {
                val mkdir = file.mkdirs()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }

        }
    }
}