package com.uniting.android.DB.DataAccessObject

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniting.android.DB.Entity.Joined

@Dao
interface JoinedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(joined: Joined)

    @Query("DELETE FROM joined WHERE room_id = :roomId AND user_id = :userId")
    fun deleteById(roomId : String, userId : String)

    @Query("SELECT * FROM joined")
    fun getAllElement() : LiveData<List<Joined>>
}