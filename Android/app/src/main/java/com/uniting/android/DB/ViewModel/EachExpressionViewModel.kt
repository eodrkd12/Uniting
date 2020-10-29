package com.uniting.android.DB.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.uniting.android.DB.Entity.Chat
import com.uniting.android.DB.Entity.EachExpression
import com.uniting.android.DB.Repository.ChatRepository
import com.uniting.android.DB.Repository.EachExpressionRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EachExpressionViewModel(app: Application): AndroidViewModel(app) {

    private val disposable: CompositeDisposable = CompositeDisposable()

    private val repository: EachExpressionRepository by lazy {
        EachExpressionRepository(app)
    }

    private val elements: LiveData<List<EachExpression>> by lazy {
        repository.getAllElement()
    }

    fun getAllElement() = elements

    fun insert(element: EachExpression, next: () -> Unit) {
        disposable.add( repository.insert(element).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { next() }
        )
    }

    fun delete(userId: String,partnerId: String, next: () -> Unit) {
        disposable.add( repository.delete(userId, partnerId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { next() }
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}