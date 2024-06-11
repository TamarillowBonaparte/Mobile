package com.dicoding.giziwise.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.giziwise.adapter.UserRepository
import com.dicoding.giziwise.data.Result
import com.dicoding.giziwise.pref.UserModel
import com.dicoding.giziwise.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun login(email: String, pass: String): LiveData<Result<LoginResponse>> {
        return repository.login(email, pass)
    }
}