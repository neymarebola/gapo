package com.example.btvn3.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

@androidx.databinding.BindingAdapter("setImageProfile")
fun setImageProfileHeader (view: ImageView, url: String) {
    Glide.with(view.context).load(url).into(view)
}