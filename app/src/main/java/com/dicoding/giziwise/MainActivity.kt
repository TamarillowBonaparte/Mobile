package com.dicoding.giziwise

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge

import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import com.dicoding.giziwase.databinding.ActivityMainBinding
import com.dicoding.giziwase.databinding.ActivityWelcomeBinding
import com.dicoding.giziwise.login.LoginActivity
import com.dicoding.giziwise.profile.ProfileActivity
import com.dicoding.giziwise.welcome.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        auth = Firebase.auth
        val firebaseUser = auth.currentUser


        binding.logout.setOnClickListener {
            // Hapus sesi login
            Firebase.auth.signOut()
            // Mulai aktivitas baru
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Opsional: Tutup aktivitas saat ini
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
            val credentialManager = CredentialManager.create(this@MainActivity)
            auth.signOut()
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }

    companion object {
        const val TOKEN = "token"
    }
}