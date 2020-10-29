package com.uniting.android.DB.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.uniting.android.DB.DataAccessObject.ChatDao
import com.uniting.android.DB.DataAccessObject.ImageDao
import com.uniting.android.DB.Entity.Chat
import com.uniting.android.DB.Entity.Image
import com.uniting.android.DB.UnitingDB
import io.reactivex.Observable

class ImageRepository(app : Application) {

    val dao: ImageDao by lazy{
        val db = UnitingDB.getInstance(app)!!
        db.imageDao()
    }

    val elements: LiveData<List<Image>> by lazy{
        dao.getAllElement()
    }

    fun getAllElement() : LiveData<List<Image>> {
        return elements
    }

    fun insert(element : Image) : Observable<Unit> {
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