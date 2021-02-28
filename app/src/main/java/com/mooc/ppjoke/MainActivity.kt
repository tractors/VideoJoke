package com.mooc.ppjoke

import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alibaba.fastjson.JSONObject
import com.mooc.ppjoke.ui.view.AppBottomBar
import com.mooc.ppjoke.utils.NavGraphBuilder

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var navController : NavController? = null

    lateinit var bottomBar: AppBottomBar
    override fun onCreate(savedInstanceState: Bundle?) {
        //由于 启动时设置了 R.style.launcher 的windowBackground属性
        //势必要在进入主页后,把窗口背景清理掉
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomBar: BottomNavigationView = findViewById(R.id.nav_view)

        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        navController = fragment?.let { NavHostFragment.findNavController(it) }
        NavigationUI.setupWithNavController(bottomBar, navController!!)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController!!, appBarConfiguration)
//        navView.setupWithNavController(navController!!)

        NavGraphBuilder.build(navController!!,this, fragment!!.id)

        bottomBar.setOnNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navController?.navigate(item.itemId)
        return !TextUtils.isEmpty(item.title)
    }
}