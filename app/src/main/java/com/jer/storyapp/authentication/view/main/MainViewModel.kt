package com.jer.storyapp.authentication.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jer.storyapp.authentication.data.UserRepository
import com.jer.storyapp.authentication.data.api.ListStoryItem
import com.jer.storyapp.authentication.data.api.LoginResponse
import com.jer.storyapp.authentication.data.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {


//    val storyItem = repository.storyResult
//    fun storyUser(token: String) = repository.getStories(token)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun storyUser(token: String) : LiveData<PagingData<ListStoryItem>> = repository.getStories(token).cachedIn(viewModelScope)



    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }


//    fun getSession(): LiveData<LoginResponse> {
//        return repository.getSession().asLiveData()
//    }
//
//    fun logout() {
//        viewModelScope.launch {
//            repository.logout()
//        }
//    }

}