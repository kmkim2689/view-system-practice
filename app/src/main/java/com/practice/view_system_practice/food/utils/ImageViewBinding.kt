package com.practice.view_system_practice.food.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

// https://leveloper.tistory.com/177
// https://stackoverflow.com/a/51816557
// 방법 : https://danggai.github.io/study/BindingAdapter,-Glide%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%B4-RecyclerView%EC%97%90-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0/
// JvmStatic : https://srandroid.tistory.com/375
object ImageViewBinding {
    @JvmStatic
    @BindingAdapter(
        "app:imageUrl"
        //value = ["strMealThumb"], // databinding에서 사용하고 싶은 속성명(variable의 name에 해당)
        //requireAll = false // 모든 속성을 사용할지를 나타냄. false라면, 어느 하나가 null이더라도 오류 발생x
    )
    fun ImageView.bindImageFromUrl(imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)
        }
    }
}