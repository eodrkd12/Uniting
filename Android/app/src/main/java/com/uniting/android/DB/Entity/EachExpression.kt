package com.uniting.android.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "eachexpression", primaryKeys = arrayOf("user_id","partner_id"))
data class EachExpression(
    val user_id : String,
    val partner_id : String,
    val expression_type : String,
    val expression_date : String
)