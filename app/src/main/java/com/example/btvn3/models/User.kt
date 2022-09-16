package com.example.btvn3.models

data class User(
    var id: Int = 0,
    var display_name: String = "",
    var cover: String = "",
    var avatar: String = "",
    var is_follow: Int = 0
)
