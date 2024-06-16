package com.dicoding.giziwise.bmiinput

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ViewModelFactory
import com.dicoding.giziwase.databinding.ActivityInputBinding
import com.dicoding.giziwise.bmi.bmiActivity
import com.dicoding.giziwise.data.Result
import com.dicoding.giziwise.login.LoginActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
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

        binding.etDob.setOnClickListener {
            showDatePickerDialog()
        }

        binding.btnConfirm.setOnClickListener {
            with(binding) {
                val gender = spinnerGender.selectedItem.toString()
                val dob = etDob.text.toString()
                val height = etHeight.text.toString()
                val weight = etWeight.text.toString()

                val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val date: Date = format.parse(dob)

                viewModel.getSession().observe(this@InputActivity) {
                    viewModel.inputbmiawal(weight.toInt(), height.toInt(), date, gender, it.token).observe(this@InputActivity) { result ->
                        when (result) {
                            is Result.Loading -> {}
                            is Result.Success -> {
                                setupAction()
                            }
                            is Result.Error -> {}
                        }
                    }
                }
            }
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

    private fun updateDobEditText() {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        binding.etDob.setText(sdf.format(calendar.time))
    }
}