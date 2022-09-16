package com.example.btvn3.api_service

import com.example.btvn3.models.Datum
import com.example.btvn3.models.Root
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("feed")
    fun getListNewFeed(): Call<Root>

    @GET("detail")
    fun getDetailNewFeed(): Call<Datum>
}