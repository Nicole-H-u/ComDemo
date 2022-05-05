package com.example.common.network

import android.util.Log
import com.example.common.network.config.LocalCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TAG = "RetrofitManager"

object RetrofitManager {

    private val mOkClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .followRedirects(false)
        .cookieJar(LocalCookieJar())
        .addInterceptor(HttpLoggingInterceptor { message -> Log.d(TAG, "log: $message") }.setLevel(HttpLoggingInterceptor.Level.BODY)).build()

    private var mRetrofit: Retrofit? = null


    fun initRetrofit(): RetrofitManager {
        mRetrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .client(mOkClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return this
    }

    fun <T> getService(serviceClass: Class<T>): T {
        if (mRetrofit == null) {
            throw UninitializedPropertyAccessException("Retrofit必须初始化")
        } else {
            return mRetrofit!!.create(serviceClass)
        }
    }

     fun getData(testService: TestService):TestResponse {
        var data= TestResponse(listOf (TestResponse.Data("", "")))
        testService.getData().enqueue(object : Callback<TestResponse> {
            override fun onResponse(call: Call<TestResponse>, response: Response<TestResponse>) {
                data = response.body()!!
                Log.e("Manager测试成功",data.toString())
            }

            override fun onFailure(call: Call<TestResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("Manager测试失败",data.toString())
            }
        })
        Log.e("Manager测试",data.toString())
        return data
    }
}