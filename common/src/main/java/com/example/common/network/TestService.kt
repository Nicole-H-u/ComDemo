package com.example.common.network

import retrofit2.Call
import retrofit2.http.GET

interface TestService {
    @GET("project/tree/json#")
    fun getData(): Call<TestResponse>
}