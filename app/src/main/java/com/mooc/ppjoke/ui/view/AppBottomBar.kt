package com.mooc.ppjoke.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.MenuItem
import androidx.core.util.rangeTo
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.mooc.ppjoke.R
import com.mooc.ppjoke.utils.AppConfig


class AppBottomBar : BottomNavigationView {

    companion object{
        val icons = intArrayOf(
            R.drawable.icon_tab_home, R.drawable.icon_tab_sofa,
                R.drawable.icon_tab_publish, R.drawable.icon_tab_find, R.drawable.icon_tab_mine)
    }
    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {}

    @SuppressLint("RestrictedApi")
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr : Int) :super(context,attributeSet,defStyleAttr){
        val bottomBar = AppConfig.getBottomBar()
        val tabs = bottomBar.tabs

        //二位数组，用来定义底部按钮在选中或者未被选中时两种状态的颜色
        val states = Array(2){ intArrayOf()}
        states[0] = intArrayOf(android.R.attr.state_selected)
        states[1] = intArrayOf()
        val colors = intArrayOf(
                //被选中的颜色
                Color.parseColor(bottomBar.activeColor),
                //按钮默认的颜色
                Color.parseColor(bottomBar.inActiveColor)
        )

        val colorStateList : ColorStateList = ColorStateList(states,colors)
        //设置颜色
        itemTextColor = colorStateList
        itemIconTintList = colorStateList

        //所有的按钮在任何时候都需要显示文本
        labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        selectedItemId = bottomBar.selectTab

        //将按钮添加到 BottomBar 上面
        for (tab in tabs) {
            if (!tab.enable){
                return
            }
            val itemId = getId(tab.pageUrl)
            if (itemId < 0) {
                return
            }
            val item = menu.add(0, itemId, tab.index, tab.title)

            item.setIcon(icons[tab.index])
        }

        //按钮 Icon 设置大小
        tabs.forEach {tab ->
            val iconSize = dp2px(tab.size)
            val menuView : BottomNavigationMenuView = getChildAt(0) as BottomNavigationMenuView
            val itemView = menuView.getChildAt(tab.index) as BottomNavigationItemView
            itemView.setIconSize(iconSize)

            if (TextUtils.isEmpty(tab.title)){
                itemView.setIconTintList(ColorStateList.valueOf(Color.parseColor(tab.tintColor)))
                itemView.setShifting(false)
            }
        }
    }

    private fun dp2px(size: Int): Int {
        val value = (context.resources.displayMetrics).density * size + 0.5f

        return value.toInt()
    }

    private fun getId(pageUrl: String) : Int {
        val destination = AppConfig.destConfig?.get(pageUrl) ?: return -1

        return destination.id
    }
}