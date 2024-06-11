package com.dicoding.giziwise.di

import android.content.Context
import com.dicoding.giziwise.adapter.UserRepository
import com.dicoding.giziwise.pref.UserPreference
import com.dicoding.giziwise.pref.dataStore
import com.dicoding.giziwise.retofit.ApiConfiig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfiig.getApiService(user.token)
        return UserRepository.getInstance(apiService, pref)
    }
}