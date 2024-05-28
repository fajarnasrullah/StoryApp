package com.jer.storyapp.authentication.view.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jer.storyapp.authentication.data.UserRepository
import com.jer.storyapp.authentication.data.pref.UserModel

class MapsViewModel(private val repository: UserRepository) : ViewModel(){
    val fromStory = repository.storyResult

    fun getLocMaps(token: String) = repository.getLocation(token)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}