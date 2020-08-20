package com.uniting.android.DB.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.uniting.android.DB.DataAccessObject.ExpressionDao
import com.uniting.android.DB.DataAccessObject.JoinedDao
import com.uniting.android.DB.Entity.Expression
import com.uniting.android.DB.Entity.Joined
import com.uniting.android.DB.UnitingDB
import io.reactivex.Observable

class JoinedRepository(app : Application) {

    val dao: JoinedDao by lazy{
        val db = UnitingDB.getInstance(app)!!
        db.joinedDao()
    }

    val elements: LiveData<List<Joined>> by lazy{
        dao.getAllElement()
    }

    fun getAllElement() : LiveData<List<Joined>> {
        return elements
    }

    fun insert(element : Joined) : Observable<Unit> {
        return Observable.fromCallable {
            dao.insert(element)
        }
    }

    fun delete(roomId: String, userId: String) : Observable<Unit> {
        return Observable.fromCallable {
            dao.deleteById(roomId, userId)
        }
    }
}