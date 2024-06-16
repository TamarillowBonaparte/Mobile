package com.dicoding.giziwise.bmiinput

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.giziwise.adapter.UserRepository
import com.dicoding.giziwise.pref.UserModel
import java.util.Date


class InputViewModel (private val repository: UserRepository): ViewModel(){
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun inputbmiawal(weight: Int, height: Int,  gender: String, dob: String,token: String) =
        repository.inputbmi(weight, height, dob, gender, token)

}