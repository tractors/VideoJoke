package com.will.videojoke.model

/**
 * 底部导航配置bean
 */
data class BottomBar(
        //被选中的颜色
        val activeColor: String,
       //默认的颜色
        val inActiveColor: String,
        //默认选中的颜色 tabId
        val selectTab: Int,
        val tabs: List<Tab>
)

data class Tab(
        //是否可用
        val enable: Boolean,
        val index: Int,
        val pageUrl: String,
        val size: Int,
        val tintColor: String?,
        val title: String
)