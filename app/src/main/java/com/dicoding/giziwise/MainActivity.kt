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
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
//    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

//        auth = Firebase.auth
//        val firebaseUser = auth.currentUser


        binding.logout.setOnClickListener {
            signOut()
        }
        binding.tambahbtn.setOnClickListener{
            startActivity(Intent(this, InputActivity::class.java))
        }


        binding.profileBtn.setOnClickListener {
            val token = intent.getStringExtra(TOKEN)
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra(ProfileActivity.TOKEN, token)
            Log.d("tokencheck", token!!)
            startActivity(intent)

            finish()
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