package com.uniting.android.DB.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.uniting.android.DB.DataAccessObject.RoomDao
import com.uniting.android.DB.Entity.Room
import com.uniting.android.DB.UnitingDB
import com.uniting.android.Room.MyRoomItem
import io.reactivex.Observable

class RoomRepository(app : Application) {

    val dao: RoomDao by lazy{
        val db = UnitingDB.getInstance(app)!!
        db.roomDao()
    }

    val elements: LiveData<List<MyRoomItem>> by lazy{
        dao.getAllElement()
    }

    fun getAllElement() : LiveData<List<MyRoomItem>> {
        return elements
    }

    fun insert(element : Room) : Observable<Unit> {
        return Observable.fromCallable {
            dao.insert(element)
        }
    }

    fun delete(id: String) : Observable<Unit> {
        return Observable.fromCallable {
            dao.deleteById(id)
        }
    }
}