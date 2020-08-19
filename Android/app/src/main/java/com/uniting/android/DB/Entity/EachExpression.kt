package com.uniting.android.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "eachexpression")
data class EachExpression(
    @PrimaryKey
    val user_id : String,
    @PrimaryKey
    val partner_id : String,
    val expression_type : String,
    val expression_date : String
)