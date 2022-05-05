package com.example.common.network

data class TestResponse(val data:List<Data>) {
    data class Data(val author:String,val name:String)
}