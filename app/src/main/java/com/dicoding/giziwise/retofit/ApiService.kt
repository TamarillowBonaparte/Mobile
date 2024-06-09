package com.dicoding.giziwise.retofit

import com.dicoding.giziwise.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("signup")
    suspend fun signup(
            @Field("email") email: String ,
            @Field("password")password: String ,
            @Field("confirm_password") confirm_password: String
    ): RegisterResponse
}