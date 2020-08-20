package com.uniting.android.DB.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.uniting.android.DB.DataAccessObject.RoomDao
import com.uniting.android.DB.DataAccessObject.UserDao
import com.uniting.android.DB.Entity.Room
import com.uniting.android.DB.Entity.User
import com.uniting.android.DB.UnitingDB
import io.reactivex.Observable

class UserRepository(app : Application) {

    val dao: UserDao by lazy{
        val db = UnitingDB.getInstance(app)!!
        db.userDao()
    }

    val elements: LiveData<List<User>> by lazy{
        dao.getAllElement()
    }

    fun getAllElement() : LiveData<List<User>> {
        return elements
    }

    fun insert(element : User) : Observable<Unit> {
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