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
import com.dicoding.giziwise.response.BmiResponse
import com.dicoding.giziwise.response.InputbmiResponse
import com.dicoding.giziwise.response.LoginResponse
import com.dicoding.giziwise.response.NutritionResponse
import com.dicoding.giziwise.response.ProfileResponse
import com.dicoding.giziwise.retofit.ApiConfiig
import com.google.gson.Gson

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
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage))
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
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage))
        }
    }

    fun profile(token: String):
            LiveData<Result<ProfileResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = ApiConfiig.getApiService(token).profile()
            emit(Result.Success(response))
        } catch (e: HttpException) {
            Log.d("profile", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun nutrition(
        namaMakanan: String,
        portionSize: String,
        token: String
    ): LiveData<Result<NutritionResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = ApiConfiig.getApiService(token).nutrition(namaMakanan, portionSize)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            Log.d("nutrition", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }


    fun inputbmi(
        weight: Int,
        height: Int,
        dob: String,
        gender: String,
        token: String
    ): LiveData<Result<InputbmiResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = ApiConfiig.getApiService(token).inputbmi(weight, height,gender,dob)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            Log.d("inputbmi", e.message.toString())
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, InputbmiResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage))
        }
    }
    fun getdata(token: String):LiveData<Result<BmiResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = ApiConfiig.getApiService(token).getbmi()
            emit(Result.Success(response))
        } catch (e: HttpException) {
            Log.d("bmi", e.message.toString())
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