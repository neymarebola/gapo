package com.example.btvn3.models

data class Datum(
    var id: String,
    var user_id: String,
    var content: String,
    var counts: Counts,
    var create_time: Int,
    var update_time: Int,
    var post_type: Int,
    var mediaData: ArrayList<MediaDatum>,
    var user: User,
    var comments: ArrayList<Comment>,
    var react_status: Int
    )