package com.uniting.android.DB.DataAccessObject

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uniting.android.DB.Entity.Room
import com.uniting.android.Room.MyRoomItem

@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(room: com.uniting.android.DB.Entity.Room)

    @Query("DELETE FROM room WHERE room_id = :roomId")
    fun deleteById(roomId : String)

    @Query("SELECT room.room_id, room_title, category, room_date, room_introduce, univ_name, maker, chat_content, chat_time FROM room LEFT OUTER JOIN chat ON room.room_id = chat.room_id AND chat.system_chat = 0 AND (chat_time) IN (SELECT MAX(chat_time) FROM chat GROUP BY room_id) ORDER BY chat_time DESC")
    fun getAllElement() : LiveData<List<MyRoomItem>>
}