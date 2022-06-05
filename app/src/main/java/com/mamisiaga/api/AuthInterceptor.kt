package com.mamisiaga.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor (val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader("Authorization", "Bearer $token")

        return chain.proceed(requestBuilder.build())
    }
}