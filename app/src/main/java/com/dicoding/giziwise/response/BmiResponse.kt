package com.dicoding.giziwise.response

data class BmiResponse(
	val databmi: Databmi,
	val status: String
)

data class Databmi(
	val calory: Any,
	val gender: String,
	val weight: Int,
	val category: String,
	val age: Int,
	val bmi: Any,
	val height: Int,
	val healthyWeightRange: String
)

