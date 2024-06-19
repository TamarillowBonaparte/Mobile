package com.dicoding.giziwise

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog

import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import com.dicoding.ViewModelFactory
import com.dicoding.giziwase.databinding.ActivityMainBinding
import com.dicoding.giziwase.databinding.ActivityWelcomeBinding
import com.dicoding.giziwise.bmiinput.InputActivity
import com.dicoding.giziwise.data.Result
import com.dicoding.giziwise.login.LoginActivity
import com.dicoding.giziwise.nutrition.NutritionActivity
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
        showLoading(false)

        binding.buttonHitung.setOnClickListener {
            with(binding) {
                val menu = editTextMenu.text.toString()
                val porsi = editTextPorsi.text.toString()
                Log.d("NutritionActivity", "Menu: $menu")
                Log.d("NutritionActivity", "Porsi: $porsi")

                if (menu.isNotEmpty() && porsi.isNotEmpty()) {
                    Log.d("NutritionActivity", "date: $menu")

                    viewModel.getsession().observe(this@MainActivity) {
                        viewModel.nutrition(menu, porsi, it.token)
                            .observe(this@MainActivity) { result ->
                                when (result) {
                                    is Result.Loading -> {
                                        showLoading(true)
                                    }

                                    is Result.Success -> {
                                        showLoading(false)
                                        val energi = result.data.dataprediksi.energi
                                        val lemak = result.data.dataprediksi.lemak
                                        val protein = result.data.dataprediksi.protein
                                        binding.textViewHasil.text = "Pada makanan $menu pada porsi $porsi gram terdapat $energi energi, $lemak lemak, $protein protein"
                                    }

                                    is Result.Error -> {
                                        showLoading(false)
                                        setupFail(result.error)
                                        Log.e("NutritionActivity", "Error: ${result.error}")
                                    }

                                    else -> {}
                                }
                            }
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Mohon isi dengan lengkap",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.tambahbtn.setOnClickListener{
            startActivity(Intent(this, InputActivity::class.java))
        }

        binding.profileBtn.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupFail(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Gagal Silahkan Coba lagi")
            setMessage(message)
            setPositiveButton("Lanjut") { _, _ ->
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            create()
            show()
        }
    }

    companion object {
        const val TOKEN = "token"
    }
}