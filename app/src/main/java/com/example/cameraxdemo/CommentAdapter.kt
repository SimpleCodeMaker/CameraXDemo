package com.example.cameraxdemo

import android.util.Log
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class CommentAdapter(datas: List<CommentBean>? = arrayListOf(
    CommentBean(1,true,"XXX","额呵呵呵"),
    CommentBean(2,true,"XXX","额呵呵呵"),
    CommentBean(3,true,"XXX","额呵呵呵"),
    CommentBean(4,true,"XXX","额呵呵呵"),
    CommentBean(5,true,"XXX","额呵呵呵"),
    CommentBean(6,true,"XXX","额呵呵呵"),
    CommentBean(7,true,"XXX","额呵呵呵"),
    CommentBean(8,true,"XXX","额呵呵呵"),
    CommentBean(9,true,"XXX","额呵呵呵"),
    CommentBean(10,true,"XXX","额呵呵呵"),
    CommentBean(11,true,"XXX","额呵呵呵"),
    CommentBean(12,true,"XXX","额呵呵呵"),
    CommentBean(13,true,"XXX","额呵呵呵"),
    CommentBean(14,true,"XXX","额呵呵呵"),
    CommentBean(15,true,"XXX","额呵呵呵"),
    CommentBean(16,true,"XXX","额呵呵呵"),
    CommentBean(17,true,"XXX","额呵呵呵")


)) :
    BaseQuickAdapter<CommentBean, BaseViewHolder>(R.layout.view_comment, datas) {
    override fun convert(helper: BaseViewHolder, item: CommentBean?) {
//        helper.setImageResource(R.id.avater_item, item!!)
        helper.setText(R.id.level,item!!.level.toString())
        helper.setVisible(R.id.icon,item!!.icon)
        helper.setText(R.id.name,item!!.name)
        helper.setText(R.id.content,item!!.comment)
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        Log.d("postiton",(position%17).toString())
        super.onBindViewHolder(holder, position%17)
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }
}