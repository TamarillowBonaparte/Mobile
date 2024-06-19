package com.dicoding.giziwise

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.giziwise.adapter.UserRepository
import com.dicoding.giziwise.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    fun getsession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
    fun nutrition(namaMakanan: String, portionSize: String, token: String) =
        repository.nutrition(namaMakanan, portionSize, token)

}