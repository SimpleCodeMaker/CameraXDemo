package com.example.leftrightleft

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class AvaterAdapter(
   var datas: List<Int>? = arrayListOf(
        R.drawable.avater1,
        R.drawable.avater2,
        R.drawable.avater3,
        R.drawable.avater4,
        R.drawable.avater5,
        R.drawable.avater6,
        R.drawable.avater7,
        R.drawable.avater8,
        R.drawable.avater9,
        R.drawable.avater10
    )
) :
    BaseQuickAdapter<Int, BaseViewHolder>(R.layout.view_avater, datas) {
    override fun convert(helper: BaseViewHolder, item: Int?) {
        helper.getView<ImageView>(R.id.avater_item).roundRectDrawable(item!!)
    }
    override fun getItemViewType(position: Int): Int {
        return 0
    }
    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position%(datas?.size?:1))
    }
}