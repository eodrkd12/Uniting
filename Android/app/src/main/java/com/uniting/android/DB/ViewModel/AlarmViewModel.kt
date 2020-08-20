package com.uniting.android.DB.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.uniting.android.DB.Entity.Alarm
import com.uniting.android.DB.Entity.User
import com.uniting.android.DB.Repository.AlarmRepository
import com.uniting.android.DB.Repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AlarmViewModel(app: Application): AndroidViewModel(app) {

    private val disposable: CompositeDisposable = CompositeDisposable()

    private val repository: AlarmRepository by lazy {
        AlarmRepository(app)
    }

    private val elements: LiveData<List<Alarm>> by lazy {
        repository.getAllElement()
    }

    fun getAllElement() = elements

    fun insert(element: Alarm, next: () -> Unit) {
        disposable.add( repository.insert(element).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { next() }
        )
    }

    fun delete(id: Int, next: () -> Unit) {
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