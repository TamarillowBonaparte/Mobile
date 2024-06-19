package com.dicoding.giziwise.nutrition

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.ViewModelFactory
import com.dicoding.giziwase.R
import com.dicoding.giziwase.databinding.ActivityNutritionBinding
import com.dicoding.giziwise.bmi.bmiActivity
import com.dicoding.giziwise.data.Result

class NutritionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNutritionBinding
    private val viewModel by viewModels<NutritionViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNutritionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(false)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nutrition_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.buttonHitung.setOnClickListener {
            with(binding) {
                val menu = editTextMenu.text.toString()
                val porsi = editTextPorsi.text.toString()
                Log.d("NutritionActivity", "Menu: $menu")
                Log.d("NutritionActivity", "Porsi: $porsi")

                if (menu.isNotEmpty() && porsi.isNotEmpty()) {
                    Log.d("NutritionActivity", "date: $menu")

                    viewModel.getsession().observe(this@NutritionActivity) {
                        viewModel.nutrition(menu, porsi, it.token)
                            .observe(this@NutritionActivity) { result ->
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
                        this@NutritionActivity,
                        "Mohon isi dengan lengkap",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
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

    private fun setupAction() {
        AlertDialog.Builder(this).apply {
            setTitle("Berhasil!")
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}