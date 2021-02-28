package com.mooc.libnetwork

import java.lang.reflect.Type

/**
 * 请求返回数据的转换接口
 */
interface Convert<T> {
    fun convert(response: String, type: Type): T?

    fun convert(response: String, clazz: Class<T>): T?
}