package com.jer.storyapp.authentication.view.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jer.storyapp.authentication.data.UserRepository
import com.jer.storyapp.authentication.data.pref.UserModel
import kotlinx.coroutines.launch

class DetailStoryViewModel (private val repository: UserRepository) : ViewModel() {

    val getDetailResult = repository.detailResult

    fun getDetail(token: String, id: String) {
        repository.getDetailStories(token, id)
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}