package com.dicoding.giziwise.register

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dicoding.giziwase.R
import com.dicoding.giziwase.databinding.ActivityRegisterBinding
import com.dicoding.giziwise.login.LoginActivity

class RegisterActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener(this)
        binding.username.onFocusChangeListener = this
        binding.password.onFocusChangeListener = this
        binding.konfirmpassword.onFocusChangeListener = this
        binding.konfirmpassword.setOnKeyListener(this)

        setupPasswordToggle()
    }

    private fun setupPasswordToggle() {
        binding.passwordToggle.setOnClickListener {
            togglePasswordVisibility(binding.password, binding.passwordToggle)
        }

        binding.confirmPasswordToggle.setOnClickListener {
            togglePasswordVisibility(binding.konfirmpassword, binding.confirmPasswordToggle)
        }
    }

    private fun togglePasswordVisibility(editText: EditText, imageView: ImageView) {
        val currentInputType = editText.inputType
        if (currentInputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            imageView.setImageResource(R.drawable.ic_eye_closes)
        } else {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            imageView.setImageResource(R.drawable.ic_eye_open)
        }
        editText.setSelection(editText.text.length)
    }

    private fun validateForm(): Boolean {
        val username = binding.username.text.toString().trim()
        val password = binding.password.text.toString().trim()
        val confirmPassword = binding.konfirmpassword.text.toString().trim()

        if (username.isEmpty()) {
            binding.username.error = "Username is required"
            return false
        }

        if (password.isEmpty()) {
            binding.password.error = "Password is required"
            return false
        }

        if (password.length < 8) {
            binding.password.error = "Password harus 8 karakter"
            return false
        }

        if (confirmPassword.isEmpty()) {
            binding.konfirmpassword.error = "Confirm Password is required"
            return false
        }

        if (password != confirmPassword) {
            binding.konfirmpassword.setError("Password tidak sama", ContextCompat.getDrawable(this, R.drawable.ic_error))
            return false
        }

        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.registerButton.id -> {
                if (validateForm()) {
                    // Perform registration logic here
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    navigateToLoginActivity()
                }
            }
        }
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (!hasFocus) {
            when (v?.id) {
                binding.username.id -> {
                    if (binding.username.text.toString().trim().isEmpty()) {
                        binding.username.error = "Username is required"
                    }
                }
                binding.password.id -> {
                    val password = binding.password.text.toString().trim()
                    if (password.isEmpty()) {
                        binding.password.error = "Password is required"
                    } else if (password.length < 8) {
                        binding.password.error = "Password harus 8 karakter"
                    }
                }
                binding.konfirmpassword.id -> {
                    val password = binding.password.text.toString().trim()
                    val confirmPassword = binding.konfirmpassword.text.toString().trim()
                    if (confirmPassword.isEmpty()) {
                        binding.konfirmpassword.error = "Confirm Password is required"
                    } else if (password != confirmPassword) {
                        binding.konfirmpassword.setError("Password tidak sama", ContextCompat.getDrawable(this, R.drawable.ic_error))
                    }
                }
            }
        }
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (v?.id == binding.konfirmpassword.id && keyCode == KeyEvent.KEYCODE_ENTER && event?.action == KeyEvent.ACTION_UP) {
            onClick(binding.registerButton)
            return true
        }
        return false
    }
}