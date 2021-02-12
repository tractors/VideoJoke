package com.will.videojoke.utils

import android.content.ComponentName
import androidx.fragment.app.FragmentActivity
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigator
import com.will.videojoke.model.Destination
import com.will.videojoke.ui.FixFragmentNavigator

class NavGraphBuilder {

    companion object{
        fun build(controller : NavController,activity : FragmentActivity,containerId : Int){
            val provider: NavigatorProvider = controller.navigatorProvider
            //val fragmentNavigator : FragmentNavigator = provider.getNavigator(FragmentNavigator::class.java)
            //使用自己定义的导航器
            val fragmentNavigator = FixFragmentNavigator(activity,activity.supportFragmentManager,containerId)
            provider.addNavigator(fragmentNavigator)
            val activityNavigator : ActivityNavigator = provider.getNavigator(ActivityNavigator::class.java)

            val navGraph : NavGraph = NavGraph(NavGraphNavigator(provider))
            val destConfig : HashMap<String,Destination> = AppConfig.destConfig!!


            destConfig.forEach{
                if (it.value.isFragment){
                    val destination : FragmentNavigator.Destination = fragmentNavigator.createDestination()
                    destination.className = it.value.className
                    destination.id = it.value.id
                    destination.addDeepLink(it.value.pageUrl)

                    navGraph.addDestination(destination)
                } else {
                    val destination : ActivityNavigator.Destination = activityNavigator.createDestination()
                    destination.id = it.value.id
                    destination.addDeepLink(it.value.pageUrl)
                    destination.setComponentName(ComponentName(AppGlobals.getApplication()!!.packageName,it.value.className))

                    navGraph.addDestination(destination)
                }

                if (it.value.asStarter){
                    navGraph.startDestination = it.value.id
                }
            }

            controller.graph = navGraph
        }
    }
}