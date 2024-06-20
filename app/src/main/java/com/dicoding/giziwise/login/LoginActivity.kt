package com.dicoding.giziwise.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.giziwase.R
import com.dicoding.giziwase.databinding.ActivityLoginBinding
import com.dicoding.giziwise.MainActivity
import com.dicoding.ViewModelFactory
import com.dicoding.giziwise.data.Result
import com.dicoding.giziwise.pref.UserModel
import com.dicoding.giziwise.register.RegisterActivity

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(false)

        binding.signupText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.email.addTextChangedListener(emailTextWatcher)

        with(binding) {
            loginButton.setOnClickListener {
                val email = email.text.toString().trim()
                val password = password.text.toString()

                if (!isValidEmail(email)) {
                    showErrorDialog("Invalid Email", "Please enter a valid email address.")
                    return@setOnClickListener
                }

                viewModel.login(email, password).observe(this@LoginActivity) {
                    when (it) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Error -> {
                            showLoading(false)
                            setupFail(it.error)
                            Log.e("loginError", it.error)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            viewModel.saveSession(UserModel(email, it.data.data.token))
                            Log.d("LoginActivity", it.data.data.token)
                            Log.d("LoginActivity", email)

                            AlertDialog.Builder(this@LoginActivity).apply {
                                setTitle("Login Berhasil")
                                setMessage(it.data.message)
                                setPositiveButton("Lanjut") { _, _ ->
                                    val intent = Intent(context, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }
                        }
                    }
                }
            }
        }
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

    private fun setupFail(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Login Gagal")
            setMessage(message)
            setPositiveButton("Lanjut") { _, _ ->
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    fun togglePasswordVisibility(view: View) {
        isPasswordVisible = !isPasswordVisible
        binding.password.transformationMethod =
            if (isPasswordVisible) null else PasswordTransformationMethod.getInstance()
        binding.Showpassword.setImageResource(
            if (isPasswordVisible) R.drawable.ic_eye_open else R.drawable.ic_eye_closes
        )
    }

    private val emailTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // No action required
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // No action required
        }

        override fun afterTextChanged(s: Editable?) {
            val email = s.toString().trim()
            if (!isValidEmail(email)) {
                binding.email.error = "Invalid email address"
            } else {
                binding.email.error = null
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@")
    }

    private fun showErrorDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}