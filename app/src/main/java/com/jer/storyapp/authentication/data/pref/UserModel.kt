package com.jer.storyapp.authentication.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val userId: String,
    val isLogin: Boolean = false
)