package com.uniting.android.DB.DataAccessObject

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniting.android.DB.Entity.Image

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: Image)

    @Query("DELETE FROM image WHERE image_id = :imageId")
    fun deleteById(imageId : Int)

    @Query("SELECT * FROM image")
    fun getAllElement() : LiveData<List<Image>>
}