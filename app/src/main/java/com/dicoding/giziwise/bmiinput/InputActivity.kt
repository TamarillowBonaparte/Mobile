package com.dicoding.giziwise.bmiinput

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ViewModelFactory
import com.dicoding.giziwase.databinding.ActivityInputBinding
import com.dicoding.giziwise.bmi.bmiActivity
import com.dicoding.giziwise.data.Result
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class InputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputBinding
    private val viewModel: InputViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private val calendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(false)

        binding.etDob.setOnClickListener {
            showDatePickerDialog()
        }

        binding.btnConfirm.setOnClickListener {
            with(binding) {
                val gender = spinnerGender.selectedItem.toString()
                val dob = etDob.text.toString()
                val height = etHeight.text.toString()
                val weight = etWeight.text.toString()

                Log.d("InputActivity", "Gender: $gender")
                Log.d("InputActivity", "dob: $dob")
                Log.d("InputActivity", "Height: $height")
                Log.d("InputActivity", "Weight: $weight")

                // Pastikan semua parameter telah diisi
                if (gender.isNotEmpty() && dob.isNotEmpty() && height.isNotEmpty() && weight.isNotEmpty()) {
                    Log.d("InputActivity", "date: $dob")

                    viewModel.getSession().observe(this@InputActivity) {
                        viewModel.inputbmiawal(weight.toInt(), height.toInt(), gender, dob, it.token).observe(this@InputActivity) { result ->
                            when (result) {
                                is Result.Loading -> {
                                    showLoading(true)}
                                is Result.Success -> {
                                    setupAction()
                                }
                                is Result.Error -> {
                                    showLoading(false)
                                    setupFail(result.error)
                                    Log.e("InputActivity", "Error: ${result.error}")
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(this@InputActivity, "Mohon lengkapi semua field", Toast.LENGTH_SHORT).show()
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
            setPositiveButton("Lanjut") { _, _ ->
                startActivity(Intent(context, bmiActivity::class.java))
                finish()
            }
            create()
            show()
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDobEditText()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun updateDobEditText() {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        binding.etDob.setText(sdf.format(calendar.time))
    }
}