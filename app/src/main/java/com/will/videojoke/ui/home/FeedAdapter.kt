package com.will.videojoke.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.will.videojoke.databinding.LayoutFeedTypeImageBinding
import com.will.videojoke.databinding.LayoutFeedTypeVideoBinding
import com.will.videojoke.model.Feed
import com.will.videojoke.ui.view.ListPlayerView

/**
 * 适配器
 */
open class FeedAdapter(private val mContext: Context, private val mCategory: String) : PagedListAdapter<Feed, FeedAdapter.ViewHolder>(ItemCallBack()) {

    private var inflater: LayoutInflater? = null

    init {
        inflater = LayoutInflater.from(mContext)
    }


    /**
     * 用于计算列表中两个条目直接差异的回调
     */
    class ItemCallBack : DiffUtil.ItemCallback<Feed>(){
        override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            //用于判断两个条目是否为相同项
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            //用于判断两个条目的内容是否相同
            return false
        }

    }

    class ViewHolder(val itemView: View, val binding: ViewDataBinding,val mCategory: String) : RecyclerView.ViewHolder(itemView) {
        var listPlayView: ListPlayerView? = null

        fun bindData(item : Feed){
            if (binding is LayoutFeedTypeImageBinding){
                binding.feed = item
                binding.feedImage.bindData(item.width,item.height,16,item.cover)
            } else if (binding is LayoutFeedTypeVideoBinding){
                binding.feed = item
                binding.listPlayerView.bindData(mCategory,item.width,item.height,item.cover!!,item.url!!)
            }
        }

        fun isVideoItem(): Boolean {
            return binding is LayoutFeedTypeVideoBinding
        }

        fun getListPlayerView(): ListPlayerView {
            return listPlayView!!
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding : ViewDataBinding? = null
        if (viewType == Feed.TYPE_IMAGE){
           binding = LayoutFeedTypeImageBinding.inflate(inflater!!)
        } else {
             binding = LayoutFeedTypeVideoBinding.inflate(inflater!!)
        }
        return ViewHolder(binding.root,binding,mCategory)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }


    override fun getItemViewType(position: Int): Int {
        val feed = getItem(position)
        return feed!!.itemType
    }
}