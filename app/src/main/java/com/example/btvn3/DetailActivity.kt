package com.example.btvn3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.btvn3.adapters.CommentAdapter
import com.example.btvn3.api_service.ApiService
import com.example.btvn3.models.Comment
import com.example.btvn3.models.Constants
import com.example.btvn3.models.Datum
import com.example.btvn3.models.Root
import kotlinx.android.synthetic.main.album_multiple_photo_layout.*
import kotlinx.android.synthetic.main.datum_type_album_layout.*
import kotlinx.android.synthetic.main.footer_layout.*
import kotlinx.android.synthetic.main.header_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {
    lateinit var getDetailApi: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        getDetailApi = retrofit.create(ApiService::class.java)

        getDatumDetail()
    }

    private fun getDatumDetail() {
        val call: Call<Datum> = getDetailApi.getDetailNewFeed()
        call.enqueue(object : Callback<Datum?> {
            override fun onResponse(call: Call<Datum?>, response: Response<Datum?>) {
                if (!response.isSuccessful()) {
                    Log.d("tag", response.code().toString())
                    return
                }
                val root: Datum? = response.body()
                if (root != null) {
                    var datum: Datum = root
                    setHeaderData(datum)
                    setStatus(datum)
                    setFooterData(datum)
                    setAlbumData(datum)
                    setListComment(datum)
                }

            }

            override fun onFailure(call: Call<Datum?>, t: Throwable) {
            }
        })
    }

    fun setHeaderData(datum: Datum) {
        Glide.with(baseContext).load(datum.user.avatar).into(iv_profile_image_datum_item)
        txt_display_name_datum_item.text = datum.user.display_name
        txt_create_time_datum_item.text = datum.create_time.toString()
    }

    fun setFooterData(datum: Datum) {
        txt_reaction_count.text = datum.counts.react_count.toString()
        txt_comment_count.text = datum.counts.comment_count.toString() + " Bình luận"
        txt_share_count.text = datum.counts.share_count.toString() + " Chia sẻ"
    }

    fun setStatus(datum: Datum) {
        txt_status.text = datum.content
    }

    fun setAlbumData(datum: Datum) {
        var listMedia = datum.mediaData
        var size = listMedia.size

        inc_album_multiple_photo_layout.visibility = View.VISIBLE
        inc_album_3_photo_layout.visibility = View.GONE
        inc_album_4_photo_layout.visibility = View.GONE
        inc_album_5_photo_layout.visibility = View.GONE
        inc_album_2_photo_layout.visibility = View.GONE

        Glide.with(baseContext).load(listMedia[0].src).into(multi_photo_first_pic)
        Glide.with(baseContext).load(listMedia[1].src).into(multi_photo_second_pic)
        Glide.with(baseContext).load(listMedia[2].src).into(multi_photo_third_pic)
        Glide.with(baseContext).load(listMedia[3].src).into(multi_photo_fourth_pic)
        Glide.with(baseContext).load(listMedia[4].src).into(multi_photo_fifth_pic)
        txt_remaining_photo_number.text = "+${size - 4}"
    }

    fun setListComment(datum: Datum) {
        var listC = mutableListOf<Comment>()
        for (comment in datum.comments) {
            listC.add(comment)
        }

        var adapter = CommentAdapter(baseContext, listC)
        rcv_comment.adapter = adapter

        var manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_comment.layoutManager = manager
    }

}