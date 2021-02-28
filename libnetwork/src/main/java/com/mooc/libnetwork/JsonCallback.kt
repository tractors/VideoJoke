package com.mooc.libnetwork

/**
 * 数据请求返回回调结果
 */
abstract class JsonCallback<T> {

    open fun onSuccess(response: ApiResponse<T>) {

    }

    open fun onError(response: ApiResponse<T>) {

    }

    open fun onCacheSuccess(response: ApiResponse<T>) {

    }
}