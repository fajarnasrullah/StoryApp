package com.jer.storyapp.authentication.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jer.storyapp.authentication.data.UserRepository
import com.jer.storyapp.authentication.data.api.ApiConfig
import com.jer.storyapp.authentication.data.api.ApiService
import com.jer.storyapp.authentication.data.api.ListStoryItem
import com.jer.storyapp.authentication.data.api.LoginResponse
import com.jer.storyapp.authentication.data.api.RegisterResponse
import com.jer.storyapp.authentication.data.pref.UserModel
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: UserRepository) : ViewModel() {



    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult
//    private val _storyResult = MutableLiveData<List<ListStoryItem>>()
//    val storyResult: LiveData<List<ListStoryItem>>
//        get() = _storyResult
//    private val apiService = ApiConfig().getApiService(ApiService::class.java)

//    suspend fun registerUser(name: String, email: String, password: String): RegisterResponse {
//        return apiService.register(name, email, password)
//    }





    fun loginUser(email: String, password: String) {

        viewModelScope.launch {
            try {
                val response = repository.loginUser(email, password)
//                repository.loginUser(email, password).message

                saveSession(UserModel(
                    response.loginResult.name,
                    response.loginResult.token,
                    response.loginResult.userId,
                    true
                ))
//                _loginResult.value = response
                _loginResult.postValue(response)

            } catch (e: HttpException) {
                // Tangani kesalahan jika terjadi
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
                val errorMessage = errorBody.message
                _loginResult.postValue(errorBody)
                Log.e("LoginViewModel", "HTTP Error: $errorMessage")
            }
        }

    }





    fun saveSession(userModel: UserModel) {
        viewModelScope.launch {
            repository.saveSession(userModel)
        }
    }


//    companion object {
//        private val TAG = LoginViewModel::class.java.simpleName
//    }


}