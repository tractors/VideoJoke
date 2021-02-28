package com.mooc.libnetwork

import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import java.lang.reflect.Type

class JsonConvert<T> : Convert<T> {
    override fun convert(response: String, type: Type): T? {
        val jsonObject = JSON.parseObject(response)
        val data = jsonObject.getJSONObject("data")
        if (data != null){
            val any = data["data"]
            return JSON.parseObject(any.toString(),type)
        }

        return JSON.parseObject(response,type)
    }

    override fun convert(response: String, clazz: Class<T>): T? {
        val jsonObject = JSON.parseObject(response)
        val data = jsonObject.getJSONObject("data")
        if (data != null){
            val any = data["data"]
            return JSON.parseObject(any.toString(),clazz)
        }

        return null
    }
}