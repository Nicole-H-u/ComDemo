package com.example.common.network.net

import androidx.lifecycle.MutableLiveData
import com.example.common.network.BaseModel

class StateLiveData<T> : MutableLiveData<BaseModel<T>>() {
}