package com.mooc.ppjoke.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mooc.ppjoke.model.BottomBar
import com.mooc.libcommon.AppGlobals.getApplication
import com.mooc.ppjoke.model.Destination
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.collections.HashMap

object AppConfig {
    private var sDestConfig: HashMap<String, Destination>? = null
    private var mBottomBar: BottomBar? = null


    /**
     * 获取底部 Tab 的信息
     */
    fun getBottomBar(): BottomBar {
        if (null == mBottomBar){
            val content : String = parseFile("main_tabs_config.json")
            mBottomBar = Gson().fromJson<BottomBar>(content, BottomBar::class.java)
        }
        return mBottomBar!!
    }

    /**
     * 返回被注解页面的数据
     */
    val destConfig: HashMap<String, Destination>?
        public get() {
            if (null == sDestConfig) {
                val content = parseFile("destination.json")
                sDestConfig =
                        Gson().fromJson<HashMap<String,Destination>>(content,object : TypeToken<HashMap<String?,Destination>>(){}.type)
//                    JSON.parseObject<HashMap<String, Destination>>(
//                        content,
//                        object :
//                            TypeReference<HashMap<String?, Destination?>?>() {}.type
//                    )
            }
            return sDestConfig
        }

    /**、
     * 获取文件内容
     */
    private fun parseFile(fileName: String): String {
        val assetManager = getApplication()!!.assets
        var stream: InputStream? = null
        var read: BufferedReader? = null
        val buffer = StringBuffer()
        var line: String? = null

        assetManager.open(fileName).let { stream ->
            InputStreamReader(stream).let {
                stream.bufferedReader().use {read ->
                    while (read.readLine().also { line = it } != null){
                        buffer.append(line)
                    }
                    read.close()
                }

            }

        }
        return buffer.toString()
    }
}