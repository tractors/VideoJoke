package com.mooc.ppjoke.model

import androidx.annotation.Nullable
import java.io.Serializable

/**
 * 评论
 */
class Comment : Serializable {
    var id = 0
    var itemId: Long = 0
    var commentId: Long = 0
    var userId: Long = 0
    var commentType = 0
    var createTime: Long = 0
    var commentCount = 0
    var likeCount = 0
    var commentText: String? = null
    var imageUrl: String? = null
    var videoUrl: String? = null
    var width = 0
    var height = 0
    var hasLiked = false
    var author: User? = null
    var ugc: Ugc? = null
        get() {
            return if (field == null) Ugc() else field
        }

    override fun equals(@Nullable other: Any?): Boolean {
        if (other == null || other !is Comment)
            return false
        val newComment = other as Comment?
        return (likeCount == newComment!!.likeCount
                && hasLiked == newComment.hasLiked
                && author != null && author == newComment.author
                && ugc != null && ugc == newComment.ugc)
    }
}