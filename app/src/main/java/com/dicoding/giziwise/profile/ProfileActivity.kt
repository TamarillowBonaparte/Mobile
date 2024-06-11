package com.dicoding.giziwise.profile

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.ViewModelFactory
import com.dicoding.giziwase.R
import com.dicoding.giziwase.databinding.ActivityProfileBinding
import com.dicoding.giziwase.databinding.ActivityRegisterBinding
import com.dicoding.giziwise.MainActivity
import com.dicoding.giziwise.data.Result
import com.dicoding.giziwise.register.RegisterViewModel

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogout.setOnClickListener{
            logout()
        }

        val token = intent.getStringExtra(TOKEN)
        getProfile(token!!)

    }

    private fun getProfile(token: String){
        with(binding) {
            viewModel.getProfile(token).observe(this@ProfileActivity) { result ->
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        tvNameValue.text = result.data.data.nama
                        tvEmailValue.text = result.data.data.email
                        tvGenderValue.text = result.data.data.bmi.gender
                        tvDateValue.text = result.data.data.bmi.dob
                        tvTinggiValue.text = result.data.data.bmi.height.toString()
                        tvBeratValue.text = result.data.data.bmi.weight.toString()
                        Log.d("ProfileActivity", result.data.data.nama)
                        Log.d("ProfileActivity", result.data.data.email)
                        Log.d("ProfileActivity", result.data.data.bmi.gender)
                        Log.d("ProfileActivity", result.data.data.bmi.dob)
                        Log.d("ProfileActivity", result.data.data.bmi.height.toString())
                        Log.d("ProfileActivity", result.data.data.bmi.weight.toString())
                    }
                    is Result.Error -> {
                        Log.e("profile",result.error)
                    }
                }
            }
        }
    }

    fun logout(){
        viewModel.logout()
    }


    companion object {
        const val TOKEN = "token"
    }
}