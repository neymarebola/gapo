package com.example.btvn3.views

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.btvn3.R
import com.example.btvn3.adapters.DatumAdapter
import com.example.btvn3.api_service.ApiService
import com.example.btvn3.databinding.ActivityMainBinding
import com.example.btvn3.models.Constants
import com.example.btvn3.models.Datum
import com.example.btvn3.models.Root
import com.example.btvn3.viewmodels.PostViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    lateinit var getDatumApi: ApiService
    private lateinit var listDatum: MutableList<Datum>
    private lateinit var adapter: DatumAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        getDatumApi = retrofit.create(ApiService::class.java)

        listDatum = mutableListOf()
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rcv_list_datum.layoutManager = layoutManager
        rcv_list_datum.isNestedScrollingEnabled = false

        adapter = DatumAdapter(this, listDatum)
        rcv_list_datum.adapter = adapter
        getListDatum()

        viewmodel = ViewModelProvider(this).get(PostViewModel::class.java)

        refreshApp()

        if(layoutManager.findFirstCompletelyVisibleItemPosition()==0){
            // Its at top
            Toast.makeText(this, "top", Toast.LENGTH_SHORT).show()
        }

    }

    fun refreshApp() {
        binding.swipeRefreshData.setColorSchemeResources(R.color.accentWorkSecondary)
        binding.swipeRefreshData.setOnRefreshListener {
            viewmodel.readAllData.observe(this, Observer { post ->
                adapter.setData(post)
                binding.swipeRefreshData.isRefreshing = false
            })

        }
    }

    private fun getListDatum() {
        val call: Call<Root> = getDatumApi.getListNewFeed()
        call.enqueue(object : Callback<Root?> {
            override fun onResponse(call: Call<Root?>, response: Response<Root?>) {
                if (!response.isSuccessful()) {
                    Log.d("tag", response.code().toString())
                    return
                }
                val root: Root? = response.body()
                listDatum.clear()
                if (root != null) {
                    for (item in root.data) {
                        listDatum.add(item)
                        // add post to database
                        viewmodel.addPost(item)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Root?>, t: Throwable) {
                Log.d("tag", t.message!!)
            }
        })
    }
}