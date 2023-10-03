package com.practice.view_system_practice.retrofit

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {

    companion object {
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

        // 클래스의 인스턴스가 처음으로 생성될 시 먼저 호출
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())) // JSON <-> Kotlin
                .build()
        }
    }
}