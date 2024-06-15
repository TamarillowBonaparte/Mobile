package com.dicoding.giziwise.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("dataakun")
	val dataakun: Dataakun,

	@field:SerializedName("status")
	val status: String
)

data class Bmi(

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("dob")
	val dob: String,

	@field:SerializedName("weight")
	val weight: Int,

	@field:SerializedName("age")
	val age: Int,

	@field:SerializedName("height")
	val height: Int
)

data class Dataakun(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("bmi")
	val bmi: Bmi
)
