package com.example.btvn3.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.btvn3.database.PostDatabase
import com.example.btvn3.database.PostRepository
import com.example.btvn3.models.Datum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Datum>>
    private val repository: PostRepository

    init {
        val personDao = PostDatabase.getInstance(application).postDao
        repository = PostRepository(personDao)
        readAllData = repository.readAllPost
    }

    fun addPost(post: Datum) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPost(post)
        }
    }

}