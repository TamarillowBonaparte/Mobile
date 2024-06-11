package com.dicoding.giziwise.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.giziwise.adapter.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    fun getProfile (token: String) = repository.profile(token)

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}