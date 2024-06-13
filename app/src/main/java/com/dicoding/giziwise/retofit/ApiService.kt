package com.dicoding.giziwise.retofit

import com.dicoding.giziwise.response.InputbmiResponse
import com.dicoding.giziwise.response.LoginResponse
import com.dicoding.giziwise.response.ProfileResponse
import com.dicoding.giziwise.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.Date

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

    @FormUrlEncoded
    @GET("profile")
    suspend fun profile(
        @Header("Authorization") token: String
    ): ProfileResponse

    @FormUrlEncoded
    @POST("bmi")
    suspend fun inputbmi(
        @Field("weight") weight: Int,
        @Field("height") height: Int,
        @Field("dob") dob: Date,
        @Field("gender") gender: String
    ): InputbmiResponse
}