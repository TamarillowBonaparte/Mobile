package com.dicoding.giziwise.register

import androidx.lifecycle.ViewModel
import com.dicoding.giziwise.adapter.UserRepository

class RegisterViewModel(private val repository: UserRepository): ViewModel() {
    fun register(nama: String, email: String, password: String, confirmPassword: String) =
        repository. signup(nama,email, password,confirmPassword)

}