package com.dicoding.giziwise.register

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ViewModelFactory
import com.dicoding.giziwase.R
import com.dicoding.giziwase.databinding.ActivityRegisterBinding
import com.dicoding.giziwise.data.Result
import com.dicoding.giziwise.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        showLoading(false)

        binding.registerButton.setOnClickListener {
            val nama = binding.nama.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmpassword.text.toString()

            if (!isValidEmail(email)) {
                binding.email.error = "Email tidak valid"
                return@setOnClickListener
            }

            if (password == confirmPassword) {
                viewModel.register(nama, email, password, confirmPassword).observe(this) { result ->
                    when (result) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> {
                            showLoading(false)
                            setupAction(result.data.message)
                            Log.d("registerBerhasil", result.data.message)
                        }
                        is Result.Error -> {
                            showLoading(false)
                            setupFail(result.error)
                            Log.d("registerGagal", result.error)
                        }
                    }
                }
            } else {
                binding.confirmpassword.error = "Password tidak sama"
                Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show()
            }
        }

        binding.Showpassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            updatePasswordVisibility()
        }

        binding.ShowconfirmPassword.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            updateConfirmPasswordVisibility()
        }

        binding.confirmpassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = binding.password.text.toString()
                val confirmPassword = s.toString()
                if (password != confirmPassword) {
                    binding.confirmpassword.error = "Password tidak sama"
                } else {
                    binding.confirmpassword.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun updatePasswordVisibility() {
        if (isPasswordVisible) {
            binding.password.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.Showpassword.setImageResource(R.drawable.ic_eye_open)
        } else {
            binding.password.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.Showpassword.setImageResource(R.drawable.ic_eye_closes)
        }
        binding.password.setSelection(binding.password.text.length)
    }

    private fun updateConfirmPasswordVisibility() {
        if (isConfirmPasswordVisible) {
            binding.confirmpassword.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.ShowconfirmPassword.setImageResource(R.drawable.ic_eye_open)
        } else {
            binding.confirmpassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.ShowconfirmPassword.setImageResource(R.drawable.ic_eye_closes)
        }
        binding.confirmpassword.setSelection(binding.confirmpassword.text.length)
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun setupAction(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Yee, Akun berhasil dibuat!")
            setMessage(message)
            setPositiveButton("Lanjut") { _, _ ->
                startActivity(Intent(context, LoginActivity::class.java))
                finish()
            }
            create()
            show()
        }
    }

    private fun setupFail(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Mohon maaf akun gagal dibuat!")
            setMessage(message)
            setPositiveButton("Lanjut") { _, _ -> }
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
