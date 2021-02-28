package com.mooc.libnetwork

import java.lang.StringBuilder
import java.net.URLEncoder

/**
 * 拼接 url
 */
class UrlCreator {

    companion object {
        fun createUrlFromParams(url: String, params: Map<String, Any?>): String {
            val builder = StringBuilder()
            builder.append(url)

            if (url.indexOf("?") > 0 || url.indexOf("&") > 0) {
                builder.append("&")
            } else {
                builder.append("?")
            }

            //由于 value 是 Any 类型的，所以需要转为 UTF-8
            for (map in params) {
                val value = URLEncoder.encode(map.value.toString(), "UTF-8")
                builder.append(map.key).append("=").append(value).append("&")
            }

            builder.deleteCharAt(builder.length - 1)

            return builder.toString()
        }
    }
}