package com.example.login.repo

import com.example.common.base.BaseRepository
import com.example.common.network.net.StateLiveData
import com.example.service.LoginResp


class LoginRepo( private val service:LoginApi) : BaseRepository() {

    suspend fun login(userName: String, password: String, stateLiveData: StateLiveData<LoginResp>) {
        executeResp({ service.login(userName, password) }, stateLiveData)
    }
}