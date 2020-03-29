package com.example.leftrightleft

import android.util.Log
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class CommentAdapter(var datas: List<CommentBean>? = arrayListOf(
    CommentBean(1,true,"女娲","原来新的历史不再有我"),
    CommentBean(2,true,"百里守约","最后一次，失约"),
    CommentBean(3,true,"公孙离","要幸福"),
    CommentBean(4,true,"刘备","出来混，重要的是讲义气"),
    CommentBean(5,true,"甄姬","请不要，抛弃我"),
    CommentBean(6,true,"后羿","周日被我射熄火了，所以今天是周一"),
    CommentBean(7,true,"孙悟空","你有妖气"),
    CommentBean(8,true,"蔡文姬","穿越战场的美少女~"),
    CommentBean(9,true,"妲己","啊，被玩坏了"),
    CommentBean(10,true,"刘禅","小小少年，没有烦恼，万事都有老爹罩"),
    CommentBean(11,true,"王昭君","心已经融化"),
    CommentBean(12,true,"貂蝉","你要爱上妾身哦"),
    CommentBean(13,true,"露娜","心，还给你"),
    CommentBean(14,true,"钟无艳","让姐找点乐子吧"),
    CommentBean(15,true,"牛魔","牛气冲天，纯爷们"),
    CommentBean(16,true,"高渐离","来，听离哥，替对面奏响离歌"),
    CommentBean(17,true,"亚瑟","圣光，你有看到那个敌人吗")


)) :
    BaseQuickAdapter<CommentBean, BaseViewHolder>(R.layout.view_comment, datas) {
    override fun convert(helper: BaseViewHolder, item: CommentBean?) {
//        helper.setImageResource(R.id.avater_item, item!!)
//        helper.setText(R.id.level,item!!.level.toString())
        helper.setVisible(R.id.icon,item!!.icon)
        helper.setText(R.id.name,item!!.name)
        helper.setText(R.id.content,item!!.comment)
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position%(datas?.size?:1))
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }
}