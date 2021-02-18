package com.will.videojoke.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedList.BoundaryCallback
import com.will.videojoke.databinding.LayoutFeedTypeVideoBinding
import com.will.videojoke.ui.view.ListPlayerView

/**
 * ViewModel
 */
@Suppress("UNCHECKED_CAST")
abstract class AbsViewModel<K,V> : ViewModel {
    var dataSource : DataSource<K, V>? = null
    //列表数据
    val pageData : LiveData<PagedList<V>>
    //监听数据的边界
    val boundaryPageData = MutableLiveData<Boolean>()
    var config : PagedList.Config = PagedList.Config.Builder()
        .setPageSize(10)
        .setInitialLoadSizeHint(12)// .setMaxSize(100)； //总共多少条数据// .setEnablePlaceholders(false)//占位符 // .setPrefetchDistance()//距离底部多少条数据开始加载数据
        .build()
    constructor(){
        // 通过 pageData.observe() 加载分页数据
        pageData = LivePagedListBuilder<K,V>(factory,config)
            .setInitialLoadKey(0 as K)
            .setBoundaryCallback(callback)
            .build()

    }

    private var factory = object : DataSource.Factory<K,V>(){
        override fun create(): DataSource<K, V> {
            if (dataSource == null || dataSource!!.isInvalid){
                dataSource = createDataSource()
            }

            return dataSource!!
        }

    }

    abstract fun createDataSource(): DataSource<K, V>?

    override fun onCleared() {
        super.onCleared()
    }

    fun getBoundaryPageData():LiveData<Boolean>{
        return boundaryPageData
    }

    //PagedList数据被加载 情况的边界回调callback
    //但 不是每一次分页 都会回调这里，具体请看 ContiguousPagedList#mReceiver#onPageResult
    //deferBoundaryCallbacks
    var callback : BoundaryCallback<V> = object : BoundaryCallback<V>(){
        override fun onZeroItemsLoaded() {
            //新提交的PagedList中没有数据
            boundaryPageData.postValue(false)
        }

        override fun onItemAtFrontLoaded(itemAtFront: V) {
            //新提交的PagedList中第一条数据被加载到列表上
            boundaryPageData.postValue(true)
        }

        override fun onItemAtEndLoaded(itemAtEnd: V) {
            //新提交的PagedList中最后一条数据被加载到列表上
        }

    }

}