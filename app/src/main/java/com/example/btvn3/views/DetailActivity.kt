package com.example.btvn3.views
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.btvn3.R
import com.example.btvn3.adapters.CommentAdapter
import com.example.btvn3.api_service.ApiService
import com.example.btvn3.databinding.ActivityDetailBinding
import com.example.btvn3.models.Comment
import com.example.btvn3.models.Constants
import com.example.btvn3.models.Datum
import com.github.marlonlom.utilities.timeago.TimeAgo
import kotlinx.android.synthetic.main.album_multiple_photo_layout.*
import kotlinx.android.synthetic.main.footer_layout.*
import kotlinx.android.synthetic.main.header_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {
    lateinit var getDetailApi: ApiService
    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        getDetailApi = retrofit.create(ApiService::class.java)

        getDatumDetail()
        binding.imgArrowBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
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
        txt_create_time_datum_item.text = TimeAgo.using(datum.create_time.toLong())
    }

    fun setFooterData(datum: Datum) {
        txt_reaction_count.text = datum.counts.react_count.toString()
        txt_comment_count.text = datum.counts.comment_count.toString() + " Bình luận"
        txt_share_count.text = datum.counts.share_count.toString() + " Chia sẻ"
    }

    fun setStatus(datum: Datum) {
        binding.albumLayoutDetailActivity.txtStatus.text = datum.content
    }

    fun setAlbumData(datum: Datum) {
        var listMedia = datum.mediaData
        var size = listMedia.size

        binding.albumLayoutDetailActivity.incAlbumMultiplePhotoLayout.visibility = View.VISIBLE
        binding.albumLayoutDetailActivity.incAlbum3PhotoLayout.visibility = View.GONE
        binding.albumLayoutDetailActivity.incAlbum4PhotoLayout.visibility = View.GONE
        binding.albumLayoutDetailActivity.incAlbum5PhotoLayout.visibility = View.GONE
        binding.albumLayoutDetailActivity.incAlbum2PhotoLayout.visibility = View.GONE

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
        binding.albumLayoutDetailActivity.rcvComment.adapter = adapter

        var manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.albumLayoutDetailActivity.rcvComment.layoutManager = manager
    }

}