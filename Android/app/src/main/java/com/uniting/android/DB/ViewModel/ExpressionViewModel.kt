package com.uniting.android.DB.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.uniting.android.DB.Entity.EachExpression
import com.uniting.android.DB.Entity.Expression
import com.uniting.android.DB.Repository.EachExpressionRepository
import com.uniting.android.DB.Repository.ExpressionRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ExpressionViewModel(app: Application): AndroidViewModel(app) {

    private val disposable: CompositeDisposable = CompositeDisposable()

    private val repository: ExpressionRepository by lazy {
        ExpressionRepository(app)
    }

    private val elements: LiveData<List<Expression>> by lazy {
        repository.getAllElement()
    }

    fun getAllElement() = elements

    fun insert(element: Expression, next: () -> Unit) {
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