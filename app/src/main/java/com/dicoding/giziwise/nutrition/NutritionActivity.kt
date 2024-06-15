package com.dicoding.giziwise.nutrition

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.ViewModelFactory
import com.dicoding.giziwase.R
import com.dicoding.giziwase.databinding.ActivityNutritionBinding

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nutrition_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.buttonHitung.setOnClickListener {
            with(binding) {
                val menu = editTextMenu.text.toString()
                val porsi = editTextPorsi.text.toString()
                viewModel.nutrition(menu, porsi)
            }
        }
    }
}