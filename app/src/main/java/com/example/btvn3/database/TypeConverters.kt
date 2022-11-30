package com.example.btvn3.database

import androidx.room.TypeConverter
import com.example.btvn3.models.Comment
import com.example.btvn3.models.Counts
import com.example.btvn3.models.MediaDatum
import com.example.btvn3.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlin.collections.ArrayList

class TypeConverters {

    @TypeConverter
    fun countsToJson(counts: Counts): String {
        return Gson().toJson(counts)
    }

    @TypeConverter
    fun jsonToCounts(json: String): Counts {
        return Gson().fromJson(json, Counts::class.java)
    }

    @TypeConverter
    fun userToJson(user: User): String {
        return Gson().toJson(user)
    }

    @TypeConverter
    fun jsonToUser(json: String): User {
        return Gson().fromJson(json, User::class.java)
    }

    @TypeConverter
    fun listMediaDatumToJson(list: ArrayList<MediaDatum>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    // json to list object
    fun jsonToListMediaDatum(json: String): ArrayList<MediaDatum> {
        val gson = Gson()
        val objects: Type = object : TypeToken<ArrayList<MediaDatum?>?>() {}.type
        val listObj: ArrayList<MediaDatum> = gson.fromJson(json, objects)
        return listObj
    }

    @TypeConverter
    fun listCommentToJson(list: ArrayList<Comment>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    // json to list object
    fun jsonToListComment(json: String): ArrayList<Comment> {
        val gson = Gson()
        val objects: Type = object : TypeToken<ArrayList<Comment?>?>() {}.type
        val listObj: ArrayList<Comment> = gson.fromJson(json, objects)
        return listObj
    }
}