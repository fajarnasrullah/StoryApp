package com.jer.storyapp.authentication.view.upload

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jer.storyapp.authentication.data.UserRepository
import com.jer.storyapp.authentication.data.api.UploadResponse
import com.jer.storyapp.authentication.data.pref.UserModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class UploadViewModel(private val repository: UserRepository) : ViewModel() {

    private val _fileUploadResponse = MutableLiveData<UploadResponse>()
    val fileUploadResponse: LiveData<UploadResponse> = _fileUploadResponse

    fun addStory(
        token: String,
        multipartBody: MultipartBody.Part,
        description: RequestBody
    ){
        viewModelScope.launch {
            try {
                val response = repository.addStory(token, multipartBody, description)
                _fileUploadResponse.postValue( response)
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, UploadResponse::class.java)
                val errorMessage = errorBody.message

                _fileUploadResponse.postValue(errorBody)
                Log.d("TAG", "Upload File Error: $errorMessage")
            }
        }
    }

    fun getSession(): LiveData<UserModel> = repository.getSession().asLiveData()
}