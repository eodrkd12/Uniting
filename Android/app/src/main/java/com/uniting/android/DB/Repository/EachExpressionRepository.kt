package com.uniting.android.DB.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.uniting.android.DB.DataAccessObject.EachExpressionDao
import com.uniting.android.DB.Entity.EachExpression
import com.uniting.android.DB.UnitingDB
import io.reactivex.Observable

class EachExpressionRepository(app : Application) {

    val dao: EachExpressionDao by lazy{
        val db = UnitingDB.getInstance(app)!!
        db.eachExpressionDao()
    }

    val elements: LiveData<List<EachExpression>> by lazy{
        dao.getAllElement()
    }

    fun getAllElement() : LiveData<List<EachExpression>> {
        return elements
    }

    fun insert(element : EachExpression) : Observable<Unit> {
        return Observable.fromCallable {
            dao.insert(element)
        }
    }

    fun delete(userId: String, partnerId: String) : Observable<Unit> {
        return Observable.fromCallable {
            dao.deleteById(userId, partnerId)
        }
    }
}