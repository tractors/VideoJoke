package com.mooc.ppjoke.model

import androidx.annotation.Nullable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import java.io.Serializable

class Ugc : BaseObservable(),Serializable {
    var likeCount = 0
    var commentCount = 0
    var hasFavorite = false
//        @Bindable
//        get
//        set(value) {
//            field = value
//            notifyPropertyChanged(BR._all)
//        }

    var shareCount = 0
        @Bindable
        get
    var hasdiss = false
        @Bindable
        get
        set(value) {
            if (field == value) return
            if (value) {
                hasLiked = false
            }
            field = value
        }
    var hasLiked = false
//        @Bindable
//        get
//        set(value) {
//            if (value) {
//                likeCount += 1
//                hasdiss = false
//            } else {
//                likeCount -= 1
//            }
//            field = value
//            notifyPropertyChanged(BR._all)
//        }


    override fun equals(@Nullable other: Any?): Boolean {
        if (other == null || other !is Ugc)
            return false
        val newUgc = other as Ugc?
        return (likeCount == newUgc!!.likeCount
                && shareCount == newUgc.shareCount
                && commentCount == newUgc.commentCount
                && hasFavorite == newUgc.hasFavorite
                && hasLiked == newUgc.hasLiked
                && hasdiss == newUgc.hasdiss)
    }


}