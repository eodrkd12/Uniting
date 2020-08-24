package com.uniting.android.DB.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.uniting.android.DB.DataAccessObject.ChatDao
import com.uniting.android.DB.Entity.Chat
import com.uniting.android.DB.UnitingDB
import io.reactivex.Observable

class ChatRepository(app : Application) {

    val dao: ChatDao by lazy{
        val db = UnitingDB.getInstance(app)!!
        db.chatDao()
    }

    val elements: LiveData<List<Chat>> by lazy{
        dao.getAllElement()
    }

    fun getAllElement() : LiveData<List<Chat>> {
        return elements
    }

    fun insert(element : Chat) : Observable<Unit> {
        return Observable.fromCallable {
            dao.insert(element)
        }
    }

    fun delete(id : Int) : Observable<Unit> {
        return Observable.fromCallable {
            dao.deleteById(id)
        }
    }

    fun getLastChat(roomId: String) : LiveData<List<Chat>> {
        return dao.getLastChat(roomId)
    }
}