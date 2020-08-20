package com.uniting.android.DB.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.uniting.android.DB.Entity.Joined
import com.uniting.android.DB.Repository.JoinedRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class JoinedViewModel(app: Application): AndroidViewModel(app) {

    private val disposable: CompositeDisposable = CompositeDisposable()

    private val repository: JoinedRepository by lazy {
        JoinedRepository(app)
    }

    private val elements: LiveData<List<Joined>> by lazy {
        repository.getAllElement()
    }

    fun getAllElement() = elements

    fun insert(element: Joined, next: () -> Unit) {
        disposable.add( repository.insert(element).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { next() }
        )
    }

    fun delete(roomId: String, userId: String, next: () -> Unit) {
        disposable.add( repository.delete(roomId, userId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { next() }
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}