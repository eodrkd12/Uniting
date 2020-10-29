package com.uniting.android.DB.DataAccessObject

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniting.android.DB.Entity.Expression

@Dao
interface ExpressionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(expression: Expression)

    @Query("DELETE FROM expression WHERE user_id = :userId AND partner_id = :partnerId")
    fun deleteById(userId : String, partnerId : String)

    @Query("SELECT * FROM expression")
    fun getAllElement() : LiveData<List<Expression>>
}