package com.example.common.base

import android.util.Log
import com.example.common.network.BaseResp
import com.example.common.network.DataState
import com.example.common.network.NetState
import com.example.common.network.net.StateLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import java.io.IOException

open class BaseRepository {

    companion object {
        private const val TAG = "BaseRepository"
    }

    /**
     * 方式二：结合Flow请求数据。
     * 根据Flow的不同请求状态，如onStart、onEmpty、onCompletion等设置baseResp.dataState状态值，
     * 最后通过stateLiveData分发给UI层。
     *
     * @param block api的请求方法
     * @param stateLiveData 每个请求传入相应的LiveData，主要负责网络状态的监听
     */
    suspend fun <T : Any> executeReqWithFlow(
        block: suspend () -> BaseResp<T>,
        stateLiveData: StateLiveData<T>
    ) {
        var baseResp = BaseResp<T>()
        flow {
            val respResult = block.invoke()
            baseResp = respResult
            Log.d(TAG, "executeReqWithFlow: $baseResp")
            baseResp.dataState = DataState.STATE_SUCCESS
            stateLiveData.postValue(baseResp)
            emit(respResult)
        }
            .flowOn(Dispatchers.IO)
            .onStart {
                Log.d(TAG, "executeReqWithFlow:onStart")
                baseResp.dataState = DataState.STATE_LOADING
                stateLiveData.postValue(baseResp)
            }
            .onEmpty {
                Log.d(TAG, "executeReqWithFlow:onEmpty")
                baseResp.dataState = DataState.STATE_EMPTY
                stateLiveData.postValue(baseResp)
            }
            .catch { exception ->
                run {
                    Log.d(TAG, "executeReqWithFlow:code  ${baseResp.errorCode}")
                    exception.printStackTrace()
                    baseResp.dataState = DataState.STATE_ERROR
                    baseResp.error = exception
                    stateLiveData.postValue(baseResp)
                }
            }
            .collect {
                Log.d(TAG, "executeReqWithFlow: collect")
                stateLiveData.postValue(baseResp)
            }


    }

    /**
     * 方式一
     * repo 请求数据的公共方法，
     * 在不同状态下先设置 baseResp.dataState的值，最后将dataState 的状态通知给UI
     * @param block api的请求方法
     * @param stateLiveData 每个请求传入相应的LiveData，主要负责网络状态的监听
     */
    suspend fun <T : Any> executeResp(
        block: suspend () -> BaseResp<T>,
        stateLiveData: StateLiveData<T>
    ) {
        var baseResp = BaseResp<T>()
        try {
            baseResp.dataState = DataState.STATE_LOADING
            //开始请求数据
            val invoke = block.invoke()
            //将结果复制给baseResp
            baseResp = invoke
            if (baseResp.errorCode == 0) {
                //请求成功，判断数据是否为空，
                //因为数据有多种类型，需要自己设置类型进行判断
                if (baseResp.data == null || baseResp.data is List<*> && (baseResp.data as List<*>).size == 0) {
                    //TODO: 数据为空,结构变化时需要修改判空条件
                    baseResp.dataState = DataState.STATE_EMPTY
                } else {
                    //请求成功并且数据为空的情况下，为STATE_SUCCESS
                    baseResp.dataState = DataState.STATE_SUCCESS
                }

            } else {
                //服务器请求错误
                baseResp.dataState = DataState.STATE_FAILED
            }
        } catch (e: Exception) {
            //非后台返回错误，捕获到的异常
            baseResp.dataState = DataState.STATE_ERROR
            baseResp.error = e
        } finally {
            stateLiveData.postValue(baseResp)
        }
    }


    /**
     * @deprecated Use {@link executeResp} instead.
     */
    suspend fun <T : Any> executeResp(
        resp: BaseResp<T>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): NetState<T> {
        return coroutineScope {
            if (resp.errorCode == 0) {
                successBlock?.let { it() }
                NetState.Success(resp.data!!)
            } else {
                Log.d(TAG, "executeResp: error")
                errorBlock?.let { it() }
                NetState.Error(IOException(resp.errorMsg))
            }
        }
    }

}