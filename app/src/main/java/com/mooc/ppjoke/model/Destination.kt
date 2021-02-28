package com.mooc.ppjoke.model

/**
 * 注册使用的Bean
 */
data class Destination (
    val isFragment : Boolean,
    val asStarter : Boolean,
    val needLogin : Boolean,
    val pageUrl: String,
    val className: String,
    val id : Int
)