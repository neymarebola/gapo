package com.example.btvn3.models

import java.time.Duration
import java.util.*

data class MediaDatum(
    var type: String,
    var image_id: String,
    var store: String,
    var path: String,
    var src: String,
    var thumb_pattern: String,
    var width: Int,
    var height: Int,
    var video_id: String,
    var duration: String,
    var available_format: AvailableFormat,
    var thumb: Thumb
)
