package com.uniting.android.DB.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.uniting.android.DB.Entity.Alarm
import com.uniting.android.DB.Entity.Image
import com.uniting.android.DB.Repository.AlarmRepository
import com.uniting.android.DB.Repository.ImageRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ImageViewModel(app: Application): AndroidViewModel(app) {

    private val disposable: CompositeDisposable = CompositeDisposable()

    private val repository: ImageRepository by lazy {
        ImageRepository(app)
    }

    private val elements: LiveData<List<Image>> by lazy {
        repository.getAllElement()
    }

    fun getAllElement() = elements

    fun insert(element: Image, next: () -> Unit) {
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