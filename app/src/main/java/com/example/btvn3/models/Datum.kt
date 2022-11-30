package com.example.btvn3.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class Datum(
    @PrimaryKey
    var id: String,

    @ColumnInfo(name = "userId")
    var user_id: String,

    @ColumnInfo(name = "content")
    var content: String,

    @ColumnInfo(name = "counts")
    var counts: Counts, // object

    @ColumnInfo(name = "createTime")
    var create_time: Int,

    @ColumnInfo(name = "updateTime")
    var update_time: Int,

    @ColumnInfo(name = "postType")
    var post_type: Int,

    @ColumnInfo(name = "mediaData")
    var mediaData: ArrayList<MediaDatum>, // list object

    @ColumnInfo(name = "user")
    var user: User, // object

    @ColumnInfo(name = "comments")
    var comments: ArrayList<Comment>, // list object

    @ColumnInfo(name = "reactStatus")
    var react_status: Int
    )