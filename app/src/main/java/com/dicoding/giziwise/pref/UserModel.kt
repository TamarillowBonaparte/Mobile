package com.dicoding.giziwise.pref

import android.content.Context

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)