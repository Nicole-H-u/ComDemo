package com.example.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.common.base.BaseViewModel
import com.example.common.network.net.StateLiveData
import com.example.login.repo.LoginRepo
import com.example.service.LoginResp
import kotlinx.coroutines.launch

class LoginViewModel(private val repo : LoginRepo) : BaseViewModel() {
    val loginLiveData = StateLiveData<LoginResp>()

    fun login(userName: String, password: String) {

        viewModelScope.launch {
            repo.login(userName, password, loginLiveData)
        }
    }
}