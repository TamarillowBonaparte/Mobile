package com.dicoding.giziwise.bmi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.giziwise.adapter.UserRepository
import com.dicoding.giziwise.pref.UserModel

class bmiViewModel  (private val repository: UserRepository): ViewModel(){
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
    fun bmiviewdata(token: String) = repository.getdata(token)

}