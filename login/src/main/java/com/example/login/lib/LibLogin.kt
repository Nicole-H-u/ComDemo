package com.example.login.lib

import com.example.common.network.RetrofitManager
import com.example.login.repo.LoginApi
import com.example.login.repo.LoginRepo
import com.example.login.viewmodel.LoginViewModel


import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module


val moduleLogin= module{
    single {
        RetrofitManager.initRetrofit().getService(LoginApi::class.java)
    }

    single {
        LoginRepo(get())
    }

    viewModel { LoginViewModel(get()) }
}