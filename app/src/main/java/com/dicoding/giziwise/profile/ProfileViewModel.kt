package com.dicoding.giziwise.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.giziwise.adapter.UserRepository
import com.dicoding.giziwise.pref.UserModel
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    fun getProfile (token: String) = repository.profile(token)

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getsesion(): LiveData<UserModel> = repository.getSession().asLiveData()
}