package com.uniting.android.DB.DataAccessObject

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uniting.android.DB.Entity.Room

@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(room: com.uniting.android.DB.Entity.Room)

    @Query("DELETE FROM room WHERE room_id = :roomId")
    fun deleteById(roomId : String)

    @Query("SELECT * FROM room")
    fun getAllElement() : LiveData<List<Room>>
}