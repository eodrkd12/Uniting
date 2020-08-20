package com.uniting.android.DB.DataAccessObject

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniting.android.DB.Entity.EachExpression

@Dao
interface EachExpressionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(eachExpression: EachExpression)

    @Query("DELETE FROM eachexpression WHERE user_id = :userId AND partner_id = :partnerId")
    fun deleteById(userId : String, partnerId : String)

    @Query("SELECT * FROM eachexpression")
    fun getAllElement() : LiveData<List<EachExpression>>
}