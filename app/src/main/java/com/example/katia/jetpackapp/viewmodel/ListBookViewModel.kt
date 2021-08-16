package com.example.katia.jetpackapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.katia.jetpackapp.database.getDatabase
import com.example.katia.jetpackapp.repository.BooksRepository
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.IllegalArgumentException

class ListBookViewModel(application: Application) : AndroidViewModel(application) {

    private val booksRepository = BooksRepository(getDatabase(application))
    val books = booksRepository.books
    private var _errorNetwork = MutableLiveData<Boolean>(false)
    val errorNetwork: LiveData<Boolean> get() = _errorNetwork

    //loader
    private var _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> get() = _loading

    init {
        refreshFromRepository()
    }

    private fun refreshFromRepository() = viewModelScope.launch {
        try {
            _loading.value = true
            booksRepository.refreshBooks()
            _loading.value = false
            _errorNetwork.value = false
        } catch (networkError: IOException) {
            if (books.value.isNullOrEmpty()) {
                _loading.value = false
                _errorNetwork.value = true
            }
        }
    }


    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ListBookViewModel::class.java)) {
                return ListBookViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct ListBookViewModel")
        }

    }

}