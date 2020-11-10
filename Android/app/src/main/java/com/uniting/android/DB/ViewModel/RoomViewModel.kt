package com.uniting.android.DB.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.uniting.android.DB.Entity.Room
import com.uniting.android.DB.Entity.User
import com.uniting.android.DB.Repository.RoomRepository
import com.uniting.android.Room.MyRoomItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RoomViewModel(app: Application): AndroidViewModel(app) {

    private val disposable: CompositeDisposable = CompositeDisposable()

    private val repository: RoomRepository by lazy {
        RoomRepository(app)
    }

    private val elements: LiveData<List<MyRoomItem>> by lazy {
        repository.getAllElement()
    }

    fun getAllElement() = elements

    fun insert(element: Room, next: () -> Unit) {
        disposable.add( repository.insert(element).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { next() }
        )
    }

    fun delete(id: String, next: () -> Unit) {
        disposable.add( repository.delete(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { next() }
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}