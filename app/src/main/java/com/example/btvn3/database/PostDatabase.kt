package com.example.btvn3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.btvn3.models.Datum
import com.example.btvn3.viewmodels.PostViewModel

@Database(entities = [Datum::class], version = 1, exportSchema = false)
@TypeConverters(com.example.btvn3.database.TypeConverters::class)
abstract class PostDatabase : RoomDatabase() {

    abstract val postDao: PostDao

    companion object {

        @Volatile
        private var INSTANCE: PostDatabase? = null

        fun getInstance(context: Context): PostDatabase {
            val instance = INSTANCE
            if (instance != null) {
                return instance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostDatabase::class.java,
                    "post_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }

}