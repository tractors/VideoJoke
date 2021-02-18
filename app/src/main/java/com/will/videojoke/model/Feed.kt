package com.will.videojoke.model

import android.text.TextUtils
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import java.io.Serializable

class Feed : BaseObservable(), Serializable {

    companion object{
        const val TYPE_IMAGE: Int = 1
        const val TYPE_VIDEO: Int = 2
    }
    var id = 0
    var itemId: Long = 0
    var itemType = 0
    var createTime: Long = 0
    var duration = 0.0
    var feeds_text: String? = null
    var authorId: Long = 0
    var activityIcon: String? = null
    var activityText: String? = null
    var width = 0
    var height = 0
    var url: String? = null
    var cover: String? = null
    var author: User? = null
    var topComment: Comment? = null
    var ugc: Ugc? = null
        @Bindable
        get() {
            if (field == null) field = Ugc()
            return field
        }


    override fun equals(other: Any?): Boolean {
        if (other == null || other is Feed) return false
        val newFeed = other as Feed
        return id == newFeed.id
                && itemId == newFeed.itemId
                && itemType == newFeed.itemType
                && createTime == newFeed.createTime
                && duration == newFeed.duration
                && TextUtils.equals(feeds_text, newFeed.feeds_text)
                && authorId == newFeed.authorId
                && TextUtils.equals(activityIcon, newFeed.activityIcon)
                && TextUtils.equals(activityText, newFeed.activityText)
                && width == newFeed.width
                && height == newFeed.height
                && TextUtils.equals(url, newFeed.url)
                && TextUtils.equals(cover, newFeed.cover)
                && (author != null && author!!.equals(newFeed.author))
                && (topComment != null && topComment!!.equals(newFeed.topComment))
                && (ugc != null && ugc!!.equals(newFeed.ugc));
    }

}