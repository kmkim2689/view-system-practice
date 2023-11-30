package com.practice.view_system_practice.food.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object MealRetrofitInstance {
    val interceptor = HttpLoggingInterceptor().apply {
        // level : BODY -> logs headers + bodies of request, response
        // NONE, BASIC, HEADERS, BODY
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
        connectTimeout(30, TimeUnit.SECONDS)
        // readTimeout : maximum time gap between 'arrivals' of two data packets when waiting for the server's response
        readTimeout(20, TimeUnit.SECONDS)
        // writeTimeout : maximum time gap between two data packets when 'sending' them to the server
        writeTimeout(25, TimeUnit.SECONDS)
    }.build()

    val api: MealApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(MealApi::class.java)
    }

}