package com.dicoding.giziwise.profile

import android.app.AlertDialog
import android.content.Intent
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
import com.dicoding.giziwise.MainActivity
import com.dicoding.giziwise.data.Result
import com.dicoding.giziwise.welcome.WelcomeActivity

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profile_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        binding.homeBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        viewModel.getsesion().observe(this) {
            getProfile(it.token)
        }
    }

    private fun getProfile(token: String) {
        with(binding) {
            viewModel.getProfile(token).observe(this@ProfileActivity) { result ->
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        tvNameValue.text = result.data.dataakun.nama
                        tvEmailValue.text = result.data.dataakun.email
                        tvGenderValue.text = result.data.dataakun.bmi.gender
                        tvDateValue.text = result.data.dataakun.bmi.dob
                        tvTinggiValue.text = result.data.dataakun.bmi.height.toString()
                        tvBeratValue.text = result.data.dataakun.bmi.weight.toString()
                        Log.d("ProfileActivitydata", result.data.dataakun.nama)
                        Log.d("ProfileActivitydata", result.data.dataakun.email)
                        Log.d("ProfileActivitydata", result.data.dataakun.bmi.gender)
                        Log.d("ProfileActivitydata", result.data.dataakun.bmi.dob)
                        Log.d("ProfileActivitydata", result.data.dataakun.bmi.height.toString())
                        Log.d("ProfileActivitydata", result.data.dataakun.bmi.weight.toString())
                    }
                    is Result.Error -> {
                        Log.e("profile", result.error)
                    }
                }
            }
        }
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("Apakah anda yakin ingin keluar?")
            setPositiveButton("Yes") { dialog, _ ->
                logout()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    private fun logout() {
        viewModel.logout()
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
    }
}
