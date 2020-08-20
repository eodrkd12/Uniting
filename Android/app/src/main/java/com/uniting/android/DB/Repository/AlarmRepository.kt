package com.uniting.android.DB.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.uniting.android.DB.DataAccessObject.AlarmDao
import com.uniting.android.DB.Entity.Alarm
import com.uniting.android.DB.UnitingDB
import io.reactivex.Observable

class AlarmRepository(app: Application) {

    val dao: AlarmDao by lazy{
        val db = UnitingDB.getInstance(app)!!
        db.alarmDao()
    }

    val elements: LiveData<List<Alarm>> by lazy{
        dao.getAllElement()
    }

    fun getAllElement() : LiveData<List<Alarm>>{
        return elements
    }

    fun insert(element : Alarm) : Observable<Unit> {
        return Observable.fromCallable {
            dao.insert(element)
        }
    }

    fun delete(id : Int) : Observable<Unit> {
        return Observable.fromCallable {
            dao.deleteById(id)
        }
    }
}