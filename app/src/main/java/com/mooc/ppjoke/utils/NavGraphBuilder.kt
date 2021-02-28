package com.mooc.ppjoke.utils

import android.content.ComponentName
import androidx.fragment.app.FragmentActivity
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigator
import com.mooc.libcommon.AppGlobals
import com.mooc.ppjoke.model.Destination
import com.mooc.ppjoke.ui.FixFragmentNavigator


class NavGraphBuilder {

    companion object{
        fun build(controller : NavController,activity : FragmentActivity,containerId : Int){
            val provider: NavigatorProvider = controller.navigatorProvider
            //获取已经注册的导航
            //val fragmentNavigator : FragmentNavigator = provider.getNavigator(FragmentNavigator::class.java)
            //使用自己定义的导航器
            val fragmentNavigator = FixFragmentNavigator(activity,activity.supportFragmentManager,containerId)
            provider.addNavigator(fragmentNavigator)
            val activityNavigator : ActivityNavigator = provider.getNavigator(ActivityNavigator::class.java)

            //节点集合
            val navGraph : NavGraph = NavGraph(NavGraphNavigator(provider))
            val destConfig : HashMap<String, Destination> = AppConfig.destConfig!!


            destConfig.forEach{
                if (it.value.isFragment){
                    val destination : FragmentNavigator.Destination = fragmentNavigator.createDestination()
                    destination.className = it.value.className
                    destination.id = it.value.id
                    destination.addDeepLink(it.value.pageUrl)
                    //添加节点
                    navGraph.addDestination(destination)
                } else {
                    val destination : ActivityNavigator.Destination = activityNavigator.createDestination()
                    destination.id = it.value.id
                    destination.addDeepLink(it.value.pageUrl)
                    destination.setComponentName(ComponentName(AppGlobals.getApplication()!!.packageName,it.value.className))

                    navGraph.addDestination(destination)
                }

                //如果是默认的启动页
                if (it.value.asStarter){
                    navGraph.startDestination = it.value.id
                }
            }

            //设置新的节点集合
            controller.graph = navGraph
        }
    }
}