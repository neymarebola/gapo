package com.example.btvn3.database

import androidx.lifecycle.LiveData
import com.example.btvn3.models.Datum

class PostRepository(private val postDao: PostDao){
    val readAllPost: LiveData<List<Datum>> = postDao.readAllData()

    suspend fun addPost(post: Datum) {
        postDao.addPost(post)
    }
}