package com.uniting.android.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class Image (
    @PrimaryKey(autoGenerate = true)
    val image_id : Int,
    val user_id : String,
    val image_url : String,
    val image_usage : String,
    val image_date : String
)
