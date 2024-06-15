package com.dicoding.giziwise.response

import com.google.gson.annotations.SerializedName

data class NutritionResponse(

	@field:SerializedName("dataprediksi")
	val dataprediksi: Dataprediksi,

	@field:SerializedName("status")
	val status: String
)

data class Dataprediksi(

	@field:SerializedName("protein")
	val protein: Any,

	@field:SerializedName("makanan")
	val makanan: String,

	@field:SerializedName("energi")
	val energi: Any,

	@field:SerializedName("lemak")
	val lemak: Any
)