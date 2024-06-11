package com.dicoding.giziwise.adapter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.giziwise.pref.UserModel
import com.dicoding.giziwise.pref.UserPreference
import com.dicoding.giziwise.response.RegisterResponse
import com.dicoding.giziwise.retofit.ApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import com.dicoding.giziwise.data.Result
import com.dicoding.giziwise.response.LoginResponse

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun signup(
        nama: String,email: String, password: String, confirm_password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.signup(nama,email, password, confirm_password)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            Log.d("register", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(
        email: String,
        pass: String
    ): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, pass)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            Log.d("login", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }


    companion object{
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference,
        ): UserRepository {
            return instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }
        }
    }
}