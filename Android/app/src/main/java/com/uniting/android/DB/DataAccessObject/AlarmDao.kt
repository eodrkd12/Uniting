package com.uniting.android.DB.DataAccessObject

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniting.android.DB.Entity.Alarm

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alarm: Alarm)

    @Query("DELETE FROM alarm WHERE alarm_id = :alarmId")
    fun deleteById(alarmId : Int)

    @Query("SELECT * FROM alarm")
    fun getAllElement() : LiveData<List<Alarm>>
}