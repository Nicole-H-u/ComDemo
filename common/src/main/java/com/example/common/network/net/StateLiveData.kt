package com.example.common.network.net

import androidx.lifecycle.MutableLiveData
import com.example.common.network.BaseResp

class StateLiveData<T> : MutableLiveData<BaseResp<T>>() {
}