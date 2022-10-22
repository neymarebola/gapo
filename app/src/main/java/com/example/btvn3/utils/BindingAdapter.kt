package com.example.btvn3.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("setImageProfile")
fun setImage(view: ImageView, url: String) {
    if (url != null)
        Glide.with(view.context).load(url).into(view)
}