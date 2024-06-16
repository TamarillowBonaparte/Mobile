package com.dicoding.giziwise.retofit

import com.dicoding.giziwise.response.BmiResponse
import com.dicoding.giziwise.response.InputbmiResponse
import com.dicoding.giziwise.response.LoginResponse
import com.dicoding.giziwise.response.NutritionResponse
import com.dicoding.giziwise.response.ProfileResponse
import com.dicoding.giziwise.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("signup")
    suspend fun signup(
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("password")password: String,
        @Field("confirmPassword") confirmPassword: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("signin")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("me")
    suspend fun profile(): ProfileResponse

    @FormUrlEncoded
    @POST("predict")
    suspend fun nutrition(
        @Field("nama_makanan") nama_makanan: String,
        @Field("portion_size") portion_size: String,
    ): NutritionResponse

    @FormUrlEncoded
    @POST("bmi")
    suspend fun inputbmi(
        @Field("weight") weight: Int,
        @Field("height") height: Int,
        @Field("gender") gender: String,
        @Field("dob") dob: String,
    ): InputbmiResponse

    @GET("bmi")
    suspend fun getbmi(
    ): BmiResponse
}