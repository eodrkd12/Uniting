package com.uniting.android.DB.DataAccessObject

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniting.android.DB.Entity.Chat

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(chat: Chat)

    @Query("DELETE FROM chat WHERE room_id = :roomId")
    fun deleteById(roomId: String)

    @Query("SELECT * FROM chat WHERE room_id = :roomId AND chat_time >= :enterDate")
    fun getAllElement(roomId: String, enterDate: String) : LiveData<List<Chat>>

    @Query("SELECT * FROM chat WHERE room_id = :roomId ORDER BY chat_time DESC LIMIT 1")
    fun getLastChat(roomId: String) : LiveData<List<Chat>>
}