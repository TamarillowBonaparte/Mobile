package com.dicoding.giziwise

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import com.dicoding.ViewModelFactory
import com.dicoding.giziwase.databinding.ActivityMainBinding
import com.dicoding.giziwase.databinding.ActivityWelcomeBinding
import com.dicoding.giziwise.bmiinput.InputActivity
import com.dicoding.giziwise.login.LoginActivity
import com.dicoding.giziwise.profile.ProfileActivity
import com.dicoding.giziwise.welcome.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

//        auth = Firebase.auth
//        val firebaseUser = auth.currentUser

//        binding.logout.setOnClickListener {
//            viewModel.logout()
//
//            // Hapus sesi login
//            Firebase.auth.signOut()
//            // Mulai aktivitas baru
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish() // Opsional: Tutup aktivitas saat ini
//        }
        binding.tambahbtn.setOnClickListener{
            startActivity(Intent(this, InputActivity::class.java))
        }

        binding.profileBtn.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signOut() {
        lifecycleScope.launch {
            viewModel.logout()
            val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val TOKEN = "token"
    }
}