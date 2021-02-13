package com.will.libnetwork

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class ApiService {

    companion object {

        var okHttpClient: OkHttpClient? = null
        var mBaseUrl : String? = null
        var mConvert: Convert<*>? = null

        fun <T> init(baseUrl : String,convert: Convert<T>?){
            this.mBaseUrl = baseUrl
            this.mConvert = convert?:JsonConvert<T>()
        }

        /**
         * 静态初始化
         */
        init {
            //日志输出
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            okHttpClient = OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()

            //Https 请求证书信任的问题
            val trustManager = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {

                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {

                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

            })

            //SSL 是Https握手流程
            val ssl = SSLContext.getInstance("SSL")
            ssl.init(null, trustManager, SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(ssl.socketFactory)
            //信任所有的域名
            HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }

        }
    }
}