package com.dicoding.giziwise.nutrition

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.giziwise.adapter.UserRepository
import com.dicoding.giziwise.pref.UserModel

class NutritionViewModel(private val repository: UserRepository) : ViewModel() {

    fun getsession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
    fun nutrition(namaMakanan: String, portionSize: String, token: String) =
        repository.nutrition(namaMakanan, portionSize, token)
}