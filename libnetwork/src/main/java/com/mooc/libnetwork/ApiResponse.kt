package com.mooc.libnetwork

/**
 * 对请求返回结果的包装类
 */
class ApiResponse<T>() {

    var success = false
    var status = 0
    var message : String? = null
    var body : T? = null

}