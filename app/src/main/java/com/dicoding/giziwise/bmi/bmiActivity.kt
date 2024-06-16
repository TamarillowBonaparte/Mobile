package com.dicoding.giziwise.bmi

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ViewModelFactory
import com.dicoding.giziwase.databinding.ActivityBmiBinding
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

        viewModel.getSession().observe(this){
            getbmi(it.token)
        }
    }
    private fun getbmi(token: String){
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

                        Log.d("bmiresultview", result.data.databmi.bmi.toString())
                        Log.d("bmiresultview", result.data.databmi.category)
                        Log.d("bmiresultview", result.data.databmi.weight.toString())
                        Log.d("bmiresultview",result.data.databmi.height.toString())
                        Log.d("bmiresultview", result.data.databmi.age.toString())
                        Log.d("bmiresultview",result.data.databmi.gender)
                        Log.d("bmiresultview", result.data.databmi.healthyWeightRange)
                        Log.d("bmiresultview", result.data.databmi.calory.toString())
                    }
                    is Result.Error -> {
                        Log.e("bmiactivity",result.error)
                    }
                }
            }
        }
    }
}