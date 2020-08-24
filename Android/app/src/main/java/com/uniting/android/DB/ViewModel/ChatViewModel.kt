package com.uniting.android.DB.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.uniting.android.DB.Entity.Chat
import com.uniting.android.DB.Repository.ChatRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ChatViewModel(app: Application): AndroidViewModel(app) {

    private val disposable: CompositeDisposable = CompositeDisposable()

    private val repository: ChatRepository by lazy {
        ChatRepository(app)
    }

    private val elements: LiveData<List<Chat>> by lazy {
        repository.getAllElement()
    }

    fun getAllElement() = elements

    fun insert(element: Chat, next: () -> Unit) {
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

    fun getLastChat(roomId: String) : LiveData<List<Chat>> {
        return repository.getLastChat(roomId)
    }
}