package com.example.btvn3.models

import java.util.*
import kotlin.collections.ArrayList

data class Comment(
    var id: String = "",
    var post_id: String = "",
    var parent_id: String = "",
    var content: String = "",
    var mediaData: ArrayList<Objects> = ArrayList(),
    var status: Int = 0,
    var counts: Counts = Counts(),
    var create_time: Int = 0,
    var update_time: Int = 0,
    var comment_as: String = "",
    var user: User = User(),
    var react_status: Int = 0,
    var react_type: ReactType = ReactType()
)
