package com.will.videojoke.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.will.libnavannotation.FragmentDestination
import com.will.libnetwork.ApiService
import com.will.videojoke.R
import com.will.videojoke.model.Feed
import com.will.videojoke.ui.AbsListFragment

@FragmentDestination(pageUrl = "main/tabs/home",asStarter = true)
class HomeFragment : AbsListFragment<Feed,HomeViewModel>() {

    private var mFeedType : String? = null
    override fun afterCreateView() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel?.mFeedType = mFeedType
    }

    override fun getAdapter(): PagedListAdapter<Feed, RecyclerView.ViewHolder> {
        val mFeedType = if (arguments == null) "all" else requireArguments().getString("feedType")

//        return object : FeedAdapter(requireContext(), feedType!!) {
//            //视图被添加到窗口时调用
//            override fun onViewAttachedToWindow(holder: ViewHolder) {
//                super.onViewAttachedToWindow(holder)
//                if (holder.isVideoItem()) {
//                    //playDetector?.addTarget(holder.getListPlayerView())
//                }
//            }
//
//            //视图被划出时
//            override fun onViewDetachedFromWindow(holder: ViewHolder) {
//                super.onViewDetachedFromWindow(holder)
//                if (holder.isVideoItem()) {
//                    //playDetector?.remoeTarget(holder.getListPlayerView())
//                }
//            }
//
//        } as PagedListAdapter<Feed, RecyclerView.ViewHolder>

        return object : FeedAdapter(requireContext(),mFeedType!!){} as PagedListAdapter<Feed, RecyclerView.ViewHolder>
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {

    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {

    }


}