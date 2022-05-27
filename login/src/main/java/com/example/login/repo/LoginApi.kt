package com.example.login.repo

import com.example.common.network.BaseResp
import com.example.service.LoginResp
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") username: String,@Field("password") password: String): BaseResp<LoginResp>

}