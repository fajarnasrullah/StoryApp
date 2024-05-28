package com.jer.storyapp.authentication.view.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jer.storyapp.authentication.data.UserRepository
import com.jer.storyapp.authentication.data.api.RegisterResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignupViewModel (private val repository: UserRepository) : ViewModel() {

    private val _registerResult = MutableLiveData<RegisterResponse>()
    val registerResult: LiveData<RegisterResponse> = _registerResult

    fun registerUser(name: String, email: String, password: String) {

        viewModelScope.launch {
            try {
                val response = repository.registerUser(name, email, password)
                _registerResult.postValue(response)
//                _registerResult.value = response
//                repository.registerUser(name, email, password).message


            } catch (e: HttpException) {
                // Tangani kesalahan jika terjadi
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
                val errorMessage = errorBody.message
                _registerResult.postValue(errorBody)
                Log.e("LoginViewModel", "HTTP Error: $errorMessage")

//                Log.d(TAG, "canLogin: $errorMessage")

            }
        }

    }
}