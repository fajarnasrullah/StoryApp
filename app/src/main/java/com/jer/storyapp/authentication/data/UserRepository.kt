package com.jer.storyapp.authentication.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.jer.storyapp.authentication.data.api.ApiConfig
import com.jer.storyapp.authentication.data.api.ApiService
import com.jer.storyapp.authentication.data.api.DetailStoryResponse
import com.jer.storyapp.authentication.data.api.ListStoryItem
import com.jer.storyapp.authentication.data.api.LoginResponse
import com.jer.storyapp.authentication.data.api.RegisterResponse
import com.jer.storyapp.authentication.data.api.Story
import com.jer.storyapp.authentication.data.api.StoryResponse
import com.jer.storyapp.authentication.data.api.UploadResponse
import com.jer.storyapp.authentication.data.pref.UserModel
import com.jer.storyapp.authentication.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class UserRepository private constructor (
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    private val _storyResult = MutableLiveData<List<ListStoryItem>>()
    val storyResult: LiveData<List<ListStoryItem>> = _storyResult
    private val _detailResult = MutableLiveData<Story>()
    val detailResult: LiveData<Story> = _detailResult



    suspend fun registerUser(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }

//    suspend fun registerUser(name: String, email: String, password: String) = apiService.register(name, email, password)

    suspend fun loginUser(email: String, password: String) = apiService.login(email,password)



    fun getStories(token: String): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
            ),
            pagingSourceFactory = {
                PagingSource(apiService, "Bearer $token")
            }
        ).liveData
    }


//    fun getStories(token: String ) {
//        val client = ApiConfig.getApiService().getStories("Bearer $token")
//        client.enqueue(object : retrofit2.Callback<StoryResponse> {
//            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
//                if (response.isSuccessful) {
//                    _storyResult.value = response.body()?.listStory
//                } else {
//                    Log.e("TAG", "onResponse: ${response.code()}, ${response.message()} ")
//                }
//            }
//            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
//                Log.e("TAG", "onFailure: ${t.message.toString()}", )
//            }
//        })
//    }
    
    fun getDetailStories(token: String, id: String) {
        val client = apiService.getDetailStory("Bearer $token", id)
        client.enqueue(object : retrofit2.Callback<DetailStoryResponse> {
            override fun onResponse(
                call: Call<DetailStoryResponse>,
                response: Response<DetailStoryResponse>
            ) {
                if (response.isSuccessful) {
                    _detailResult.value = response.body()?.story!!
                } else {
                    Log.e("TAG", "onResponse Error: ${response.code()}, ${response.message()}")

                }
            }

            override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message.toString()}", )
            }
        })
    }


    fun getLocation(token: String){
        val client = apiService.getStoriesWithLocation("Bearer $token")
        client.enqueue(object : retrofit2.Callback<StoryResponse> {
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                if (response.isSuccessful){
                    _storyResult.value = response.body()?.listStory
                }else {
                    Log.e("TAG", "onResponse: ${response.code()}, ${response.message()} ")
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message.toString()}")
            }

        })
    }

    suspend fun addStory(
        token: String,
        multipartBody: MultipartBody.Part,
        description: RequestBody
    ): UploadResponse {
        return apiService.uploadFile("Bearer $token", multipartBody, description)
    }

    suspend fun saveSession(userModel: UserModel) {
        userPreference.saveSession(userModel)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(userPreference: UserPreference, apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}