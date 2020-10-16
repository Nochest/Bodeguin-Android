package com.example.bodeguinandroid.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiGateway {
    val api = Retrofit.Builder()
        .baseUrl("http://192.168.0.16:8080/")
        .addConverterFactory(GsonConverterFactory.create()).build()
}