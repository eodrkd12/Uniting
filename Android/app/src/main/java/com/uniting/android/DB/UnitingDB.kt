package com.uniting.android.DB

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.uniting.android.DB.DataAccessObject.*
import com.uniting.android.DB.Entity.*

@Database(
    entities = [
        Alarm::class,
        Chat::class,
        EachExpression::class,
        Expression::class,
        Image::class,
        Joined::class,
        Room::class
    ], version = 1
)
abstract class UnitingDB : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
    abstract fun chatDao(): ChatDao
    abstract fun eachExpressionDao(): EachExpressionDao
    abstract fun expressionDao(): ExpressionDao
    abstract fun imageDao(): ImageDao
    abstract fun joinedDao(): JoinedDao
    abstract fun roomDao(): RoomDao
    abstract fun userDao(): UserDao

    companion object {
        var instance: UnitingDB? = null

        fun getInstance(context: Context): UnitingDB? {
            return instance ?: synchronized(UnitingDB::class) {
                instance ?: androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    UnitingDB::class.java, "uniting.db"
                ).build().also { instance = it }
            }
        }

        fun destroyInstance() {
            instance=null
        }
    }
}