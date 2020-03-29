package com.example.leftrightleft

import android.graphics.Rect
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.dip

/**
 * 圆矩形
 */
fun ImageView.roundRectDrawable(drawableID: Int)
{
    Glide.with(this.context)
        .load(drawableID)
        .apply(
            RequestOptions()
                .transforms(CenterCrop(), CircleCrop())
        )
        .into(this)
}
fun RecyclerView.itemDecoration(top: Int = 0, left: Int = 0, bottom: Int = 0, right: Int = 0) {
    addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.top = dip(top)
            outRect.right = dip(right)
            outRect.left = dip(left)
            outRect.bottom = dip(bottom)
        }
    })
}