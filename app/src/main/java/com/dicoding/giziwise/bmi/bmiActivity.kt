package com.dicoding.giziwise.bmi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dicoding.ViewModelFactory
import com.dicoding.giziwase.R
import com.dicoding.giziwase.databinding.ActivityBmiBinding
import com.dicoding.giziwise.MainActivity
import com.dicoding.giziwise.data.Result

class bmiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBmiBinding
    private val viewModel: bmiViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) {
            getbmi(it.token)
        }
        binding.backHomeBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getbmi(token: String) {
        with(binding) {
            viewModel.bmiviewdata(token).observe(this@bmiActivity) { result ->
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        bmiValue.text = result.data.databmi.bmi.toString()
                        bmipredikat.text = result.data.databmi.category
                        weightTextView.text = result.data.databmi.weight.toString()
                        heightTextView.text = result.data.databmi.height.toString()
                        ageTextView.text = result.data.databmi.age.toString()
                        genderTextView.text = result.data.databmi.gender
                        healthyWeightTextView.text = result.data.databmi.healthyWeightRange
                        calory.text = result.data.databmi.calory.toString()

                        // Set image visibility based on BMI category with fade-in animation
                        val fadeDuration = 500L

                        fun fadeIn(view: View) {
                            view.apply {
                                alpha = 0f
                                visibility = View.VISIBLE
                                animate()
                                    .alpha(1f)
                                    .setDuration(fadeDuration)
                                    .setListener(null)
                            }
                        }

                        // Reset all images to gone
                        under.visibility = View.GONE
                        normal.visibility = View.GONE
                        over.visibility = View.GONE
                        obese.visibility = View.GONE

                        // Set visibility and color based on BMI category
                        when (result.data.databmi.category) {
                            "Underweight" -> {
                                fadeIn(under)
                                bmipredikat.setTextColor(ContextCompat.getColor(this@bmiActivity, R.color.bmi_underweight))
                                bmiValue.setTextColor(ContextCompat.getColor(this@bmiActivity, R.color.bmi_underweight))
                                bmiCircle.setBackgroundResource(R.drawable.circle_biru)  // Change background to circle_biru
                            }
                            "Normal" -> {
                                fadeIn(normal)
                                bmipredikat.setTextColor(ContextCompat.getColor(this@bmiActivity, R.color.bmi_normal))
                                bmiValue.setTextColor(ContextCompat.getColor(this@bmiActivity, R.color.bmi_normal))
                                bmiCircle.setBackgroundResource(R.drawable.circle_background)  // Keep default background
                            }
                            "Overweight" -> {
                                fadeIn(over)
                                bmipredikat.setTextColor(ContextCompat.getColor(this@bmiActivity, R.color.bmi_overweight))
                                bmiValue.setTextColor(ContextCompat.getColor(this@bmiActivity, R.color.bmi_overweight))
                                bmiCircle.setBackgroundResource(R.drawable.circle_kuning)  // Change background to circle_kuning
                            }
                            "Obese" -> {
                                fadeIn(obese)
                                bmipredikat.setTextColor(ContextCompat.getColor(this@bmiActivity, R.color.bmi_obese))
                                bmiValue.setTextColor(ContextCompat.getColor(this@bmiActivity, R.color.bmi_obese))
                                bmiCircle.setBackgroundResource(R.drawable.circle_merah)  // Change background to circle_merah
                            }
                            else -> {
                                fadeIn(imageView2)
                                bmipredikat.setTextColor(ContextCompat.getColor(this@bmiActivity, R.color.bmi_normal))
                                bmiValue.setTextColor(ContextCompat.getColor(this@bmiActivity, R.color.bmi_normal))
                                bmiCircle.setBackgroundResource(R.drawable.circle_background)  // Keep default background
                            }
                        }

                        Log.d("bmiresultview", result.data.databmi.bmi.toString())
                        Log.d("bmiresultview", result.data.databmi.category)
                        Log.d("bmiresultview", result.data.databmi.weight.toString())
                        Log.d("bmiresultview", result.data.databmi.height.toString())
                        Log.d("bmiresultview", result.data.databmi.age.toString())
                        Log.d("bmiresultview", result.data.databmi.gender)
                        Log.d("bmiresultview", result.data.databmi.healthyWeightRange)
                        Log.d("bmiresultview", result.data.databmi.calory.toString())
                    }
                    is Result.Error -> {
                        Log.e("bmiactivity", result.error)
                    }
                }
            }
        }
    }
}
