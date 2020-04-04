package com.example.leftrightleft.fragment

import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.net.Uri
import android.os.Environment
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.leftrightleft.R
import com.example.leftrightleft.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_call.*


class CallFragment : BaseFragment<ViewModel>() {
    var soundPool: SoundPool? = null
    var player: MediaPlayer? = null
    var load = 0
    override fun setLayoutRes(): Int = R.layout.fragment_call
    val sound = Environment.getExternalStorageDirectory().absolutePath+"/zuoyouzuo/电话的声音.mp3"
    override fun initView() {
    }

    override fun initData() {
        //铃声初始化铃声，第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        soundPool = SoundPool(10, AudioManager.STREAM_SYSTEM, 5)
        //加载铃声
        load = soundPool?.load(context, R.raw.c2, 1) ?: 0
        //铃声加载监听，加载完成后播放
        soundPool?.setOnLoadCompleteListener { soundPool, sampleId, status ->
            load = soundPool.play(load ?: 0, 1.0f, 1.0f, 0, -1, 1f)
        }

        //初始化音频
        player = MediaPlayer.create(context!!, Uri.parse(sound))
        player?.setVolume(1f, 1f)
        //设置播放结束监听，把重复按钮显示出来
        player?.setOnCompletionListener {
            iv_repeat.visibility = View.VISIBLE
        }

    }

    override fun initEvent() {
        //点击接听按钮  停止铃声 同时播放音频  隐藏接听按钮
        iv_call.setOnClickListener {
            soundPool?.stop(load)
            it.visibility = View.GONE
            player?.start()

        }
        //点击重复按钮  播放音频 同时隐藏重复按钮
        iv_repeat.setOnClickListener {
            player?.start()
            iv_repeat.visibility = View.GONE
        }
    }

    //结束 释放资源
    override fun onDetach() {
        soundPool?.autoPause()
        soundPool?.release()
        soundPool = null

        player?.stop()
        player?.release()
        player = null
        super.onDetach()
    }

    override fun initObserve() {
    }
}