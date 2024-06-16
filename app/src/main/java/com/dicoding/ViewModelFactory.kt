package com.dicoding

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.giziwise.MainViewModel
import com.dicoding.giziwise.adapter.UserRepository
import com.dicoding.giziwise.bmi.bmiViewModel
import com.dicoding.giziwise.bmiinput.InputViewModel
import com.dicoding.giziwise.di.Injection
import com.dicoding.giziwise.login.LoginViewModel
import com.dicoding.giziwise.nutrition.NutritionViewModel
import com.dicoding.giziwise.profile.ProfileViewModel
import com.dicoding.giziwise.register.RegisterViewModel
import com.dicoding.giziwise.welcome.WelcomeViewModel

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(WelcomeViewModel::class.java) -> {
                WelcomeViewModel(repository) as T
            }
                modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(NutritionViewModel::class.java) -> {
                NutritionViewModel(repository) as T
            }
            modelClass.isAssignableFrom(bmiViewModel::class.java) -> {
                bmiViewModel(repository) as T
            }  modelClass.isAssignableFrom(InputViewModel::class.java) -> {
                InputViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}