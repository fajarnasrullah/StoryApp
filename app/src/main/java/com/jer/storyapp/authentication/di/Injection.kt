package com.jer.storyapp.authentication.di

import android.content.Context

import com.jer.storyapp.authentication.data.UserRepository
import com.jer.storyapp.authentication.data.api.ApiConfig
import com.jer.storyapp.authentication.data.pref.UserPreference
import com.jer.storyapp.authentication.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first()}
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(pref, apiService)
    }
}