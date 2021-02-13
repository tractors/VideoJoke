package com.will.libnetwork

/**
 * get的请求封装
 */
class GetRequest<T>(private val url: String) : Request<T, GetRequest<T>>(url) {
    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request {
        return builder.get().url(UrlCreator.createUrlFromParams(url, params)).build()
    }
}