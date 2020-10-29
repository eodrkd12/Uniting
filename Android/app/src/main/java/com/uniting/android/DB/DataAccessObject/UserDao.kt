package com.uniting.android.DB.DataAccessObject

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniting.android.DB.Entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(room: User)

    @Query("DELETE FROM user WHERE user_id = :userid")
    fun deleteById(userid : String)

    @Query("SELECT * FROM user")
    fun getAllElement() : LiveData<List<User>>
}