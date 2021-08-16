package com.example.katia.jetpackapp.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.katia.jetpackapp.network.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import java.lang.IllegalArgumentException
import java.net.ConnectException

class MainActivityViewModel(val application: Application) : ViewModel() {

    private val USER_KEY = "user"
    private val PASSWORD_KEY = "password"
    private val OPC_KEY = "opc"
    private val _progress: MutableLiveData<Boolean> = MutableLiveData()
    val progress: LiveData<Boolean> get() = _progress

    private val _isLogged: MutableLiveData<Boolean> = MutableLiveData()
    val isLogged: LiveData<Boolean> get() = _isLogged

    fun onButtonClicked(user: String, password: String) {
        viewModelScope.launch {
            _progress.value = true
            withContext(Dispatchers.IO) {
                try {
                    //request to the local server
                    val response = Service.RetrofitSingleton.postMethods.login(createParams(user, password))
                    _isLogged.postValue(response.isLogged)
                } catch (e: ConnectException) {
                    Log.d("error", e.message.toString())
                }
            }
            _progress.value = false
        }
    }

    fun createParams(user: String, password: String): RequestBody {
        val params: MutableMap<String, Any> = mutableMapOf()
        params.put(OPC_KEY, 1)
        params.put(USER_KEY, user)
        params.put(PASSWORD_KEY, password)
        return Service.RetrofitSingleton.createBody(params)
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                return MainActivityViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct the viewmodel")
        }
    }

}