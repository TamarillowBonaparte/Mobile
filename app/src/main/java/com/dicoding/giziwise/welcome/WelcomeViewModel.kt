package com.dicoding.giziwise.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.giziwise.adapter.UserRepository
import com.dicoding.giziwise.pref.UserModel
import kotlinx.coroutines.launch

class WelcomeViewModel(private val repository: UserRepository) : ViewModel() {
    fun getsesion(): LiveData<UserModel> = repository.getSession().asLiveData()
}