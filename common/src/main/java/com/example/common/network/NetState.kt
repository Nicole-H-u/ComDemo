package com.example.common.network

import java.lang.Exception

sealed class NetState<out T: Any> {
    data class Success<out T:Any>(val data: T): NetState<T>()
    data class Error(val exception: Exception): NetState<Nothing>()
}