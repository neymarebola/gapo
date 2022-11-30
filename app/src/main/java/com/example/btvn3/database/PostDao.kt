package com.example.btvn3.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.btvn3.models.Datum

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPost(datum: Datum)

    @Query("SELECT * FROM post_table")
    fun readAllData(): LiveData<List<Datum>>
}