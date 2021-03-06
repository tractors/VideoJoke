package com.mooc.ppjoke.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import com.mooc.libcommon.PixUtils
import com.mooc.libcommon.view.PPImageView
import com.mooc.ppjoke.R


class ListPlayerView : FrameLayout {

    private var bufferView : View? = null
    var cover : PPImageView?=null
    var blur : PPImageView? = null
    var playBtn : AppCompatImageView? = null
    var mCategory : String? = null
    var mVideoUrl : String? = null

    private var isPlaying = false
    constructor(context:Context) : this(context,null)

    constructor(context: Context,attributeSet: AttributeSet?): this(context,attributeSet,0)

    constructor(context: Context,attributeSet: AttributeSet?,defStyleAttr:Int) : super(context,attributeSet,defStyleAttr){
        LayoutInflater.from(context).inflate(R.layout.layout_player_view,this,true)
        bufferView = findViewById(R.id.buffer_view)
        cover = findViewById(R.id.cover)
        blur = findViewById(R.id.blur_background)
        playBtn = findViewById(R.id.play_btn)
        playBtn?.setOnClickListener{
            if (isPlaying){
                //inActive()
            } else {
                //onActive()
            }
        }
    }

    fun bindData(category:String,widthPx:Int,heightPx:Int,coverUrl:String,videoUrl:String){
        mCategory = category
        mVideoUrl = videoUrl

        //封面图片
        PPImageView.setImageUrl(cover!!,coverUrl,false)

        //宽小于高，则高斯模糊背景显示出来
        if (widthPx < heightPx){
            PPImageView.setBlurImageUrl(blur!!,coverUrl,10)
            blur?.visibility = View.VISIBLE
        } else {
            blur?.visibility = View.INVISIBLE
        }

        setSize(widthPx,heightPx)
    }

    protected fun setSize(widthPx: Int, heightPx: Int) {
        //这里主要是做视频宽大于高，或者高大于宽时，视频的等比缩放
        val maxWidth = PixUtils.getScreenWidth()
        val maxHeight = maxWidth

        //当前 View 的宽高
        val layoutWidth = maxWidth
        val layoutHeight : Int

        //封面的宽高
        val coverWidth : Int
        val coverHeight : Int
        //如果宽大于高
        if (widthPx >= heightPx){
            coverWidth = maxWidth
            coverHeight = (heightPx /(widthPx * 1.0f/ maxWidth)).toInt()
            layoutHeight = coverHeight
        } else {
            //宽度小于高，则高度为最大值，并计算宽度
            coverHeight = maxHeight
            layoutHeight = coverHeight
            coverWidth = (widthPx / (heightPx * 1.0f / maxHeight)).toInt()
        }

        //当前 View 的宽高
        val params = layoutParams
        params.width = layoutWidth
        params.height = layoutHeight

        //高斯模糊的宽高
        val blurParams = blur?.layoutParams
        blurParams?.width = layoutWidth
        blurParams?.height = layoutHeight

        blur?.layoutParams = blurParams

        //封面的位置 和 宽高
        val coverParams : LayoutParams = cover!!.layoutParams as LayoutParams
        coverParams.width = coverWidth
        coverParams.height = coverHeight
        coverParams.gravity = Gravity.CENTER

        cover?.layoutParams = coverParams

        //播放按钮居中
        val playBtnParams = playBtn!!.layoutParams as LayoutParams
        playBtnParams.gravity = Gravity.CENTER
        playBtn!!.layoutParams = playBtnParams

    }

}