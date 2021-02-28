@file:Suppress("NAME_SHADOWING")

package com.mooc.libnetwork

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.annotation.IntDef
import androidx.arch.core.executor.ArchTaskExecutor
import com.mooc.libnetwork.cache.CacheManager
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request.Builder
import okhttp3.Response
import java.io.IOException
import java.io.Serializable
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
abstract class Request<T,R : Request<T,R>> (private var mUrl : String) : Cloneable{
    //对header类型数据对存储
    private val headers = mutableMapOf<String,String>()
    //对参数的存储
    protected val params = mutableMapOf<String,Any?>()
    private  var cacheKey : String? = null
    private var mType: Type? = null
    private var mClazz : Class<*>? = null
    private var mCacheStrategy: Int = NET_ONLY

    companion object{
        //只访问缓存，即便本地缓存存不存在，也不会发起网络请求
        const val CACHE_ONLY = 1
        //先访问缓存，同时发起网络的请求，成功后缓存到本地
        const val CACHE_FIRST = 2
        //只访问服务器，不进行任何存储
        const val NET_ONLY = 3
        //先访问网络，成功后缓存到本地
        const val NET_CACHE = 4
    }

    @IntDef(CACHE_ONLY, CACHE_FIRST, NET_ONLY, NET_CACHE)
    annotation class CacheStrategy

    fun Request(url : String){
        mUrl = url
    }

    /**
     * 添加头文件
     */
    fun addHeader(key : String,value : String) : R{
        return this as R
    }

    /**
     * value 只能为基本数据类型
     */
    fun addParam(key: String,value: Any?) : R {
        if (value == null) return  this as R
        if (value is String){
            params[key] = value
            return this as R
        }

        //TYPE 是原始的基本类型，通过TYPE 就可以得到基本类型的 Class
        val field = value.javaClass.getField("TYPE")
        val clazz = field.get(null) as Class<*>
        //判断 clazz 是否为基本类型
        if (clazz.isPrimitive){
            params[key] = value
        }

        return this as R
    }

    fun cacheStrategy(@CacheStrategy cacheStrategy: Int): R {
        mCacheStrategy = cacheStrategy
        return this as R
    }

    /**
     * 缓存的 Key
     */
    fun cacheKey(key: String) : R{
        this.cacheKey = key
        return key as R
    }

    fun responseType(type : Type) : R{
        mType = type
        return this as R
    }

    fun responseClazz(clazz: Class<*>) : R{
        mClazz = clazz
        return this as R
    }

    /**
     * 同步请求
     */
    fun execute():ApiResponse<T>{

        if (mCacheStrategy == CACHE_ONLY){
            return readCache()
        }
        val response = getCall().execute()
        return parseResponse(response,null)
    }

    /**
     * 异步请求
     */
    @SuppressLint("RestrictedApi")
    fun execute(callback: JsonCallback<T>){

        when{
            mCacheStrategy != NET_ONLY -> {
                //异步读取缓存
                ArchTaskExecutor.getIOThreadExecutor().execute {
                    val response: ApiResponse<T> = readCache()
                    callback.onCacheSuccess(response)
                }
            }
            mCacheStrategy != CACHE_ONLY ->{
                //发起异步网络请求
                getCall().enqueue(object : Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        val response : ApiResponse<T> = ApiResponse()
                        response.message = e.message
                        callback.onError(response)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val response : ApiResponse<T> = parseResponse(response,callback)
                        if (response.success){
                            callback.onSuccess(response)
                        } else {
                            callback.onError(response)
                        }
                    }

                })
            }
        }

    }

    private fun readCache(): ApiResponse<T> {
        val key : String = if (TextUtils.isEmpty(cacheKey)) generateCacheKey() else cacheKey!!
        val cache = CacheManager.getCache(key)
        val result : ApiResponse<T> = ApiResponse<T>()
        result.status = 304
        result.message = "获取缓存成功"
        result.body = cache as T
        result.success = true
        return result
    }

    /**
     * 解析返回结果
     */
    private fun parseResponse(
        response: Response,
        callback: JsonCallback<T>?
    ): ApiResponse<T> {
        var message : String? = null
        val status = response.code
        val success = response.isSuccessful
        val result = ApiResponse<T>()
        val content = response.body?.string()
        if (success && content != null){
            val mConvert = ApiService.mConvert?:JsonConvert<T>()

            when{
                callback != null ->{
                    //获取 callback 的实际泛型类型
                    val type =
                        callback.javaClass.genericSuperclass as ParameterizedType
                    val argument = type.actualTypeArguments[0]

                    result.body = mConvert.convert(content,argument) as T
                }

                mType != null ->{
                    result.body = mConvert.convert(content, mType!!) as T
                }

                mClazz != null ->{
                    result.body = mConvert.convert(content, mClazz!!) as T
                }

                else ->{
                    result.body = content as T
                }
            }
        } else {
            message = content
        }

        result.success = success
        result.message = message
        result.status = status

        if (mCacheStrategy != NET_ONLY && result.success && result.body != null && result.body is Serializable){
            saveCache(result.body as T)
        }
        return result
    }

    private fun saveCache(body: T) {
        val key : String = if (TextUtils.isEmpty(cacheKey)) generateCacheKey() else cacheKey!!

        CacheManager.save(key,body)
    }

    /**
     * 获取 key，key 由 url + 参数构成
     */
    private fun generateCacheKey(): String {
        cacheKey = UrlCreator.createUrlFromParams(mUrl,params)
        return cacheKey!!
    }


    /**
     * 发起请求
     */
    private fun getCall() : Call {
        val builder = Builder()
        //添加头文件
        addHeaders(builder)
        //设置请求方法，get或者post
        val request = generateRequest(builder)

        return ApiService.okHttpClient!!.newCall(request)
    }

    abstract fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request

    /**
     * 添加头文件数据
     */
    private fun addHeaders(builder: Builder) {
        headers.forEach {map ->
            builder.addHeader(map.key,map.value)
        }
    }

    @Throws(CloneNotSupportedException::class)// 克隆失败抛出异常
    public override fun clone(): Request<T,R> {
        return super.clone() as Request<T, R>
    }
}