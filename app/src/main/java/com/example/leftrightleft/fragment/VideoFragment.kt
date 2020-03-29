package com.example.leftrightleft.fragment

import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.leftrightleft.R
import com.example.leftrightleft.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_video.*
import java.io.File


class VideoFragment : BaseFragment<ViewModel>() {
    val source1 = "/mnt/sdcard/zuoyouzuo/默认影片.mp4";
    override fun setLayoutRes(): Int = R.layout.fragment_video

    override fun initView() {
        val exists = File(source1).exists()
        if(exists){
            vv.setVideoURI(Uri.parse((source1)))
        }
    }

    override fun initData() {

    }

    override fun initEvent() {
        vv.setOnPreparedListener(OnPreparedListener {
            it.isLooping = true
            it.start()
        })
        vv.setOnCompletionListener(OnCompletionListener {
            //视频播放完成后的回调
        })
    }


    override fun initObserve() {
    }

    override fun onDetach() {
        vv?.suspend()
        vv?.setOnErrorListener(null)
        vv?.setOnPreparedListener(null)
        vv?.setOnCompletionListener(null)
        super.onDetach()
    }
}