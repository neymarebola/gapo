package com.example.btvn3.models

import com.google.gson.annotations.SerializedName

data class AvailableFormat(
    @SerializedName("480p")
    var _480p: String,
    @SerializedName("720p")
    var _720p: String
)
