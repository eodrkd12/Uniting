package com.uniting.android.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expression", primaryKeys = arrayOf("user_id","partner_id"))
data class Expression (
    val user_id : String,
    val partner_id : String,
    val expression_type : String,
    val expression_date : String
)