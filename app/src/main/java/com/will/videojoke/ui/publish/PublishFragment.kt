package com.will.videojoke.ui.publish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.will.libnavannotation.FragmentDestination
import com.will.videojoke.R
@FragmentDestination(pageUrl = "main/tabs/publish")
class PublishFragment :  Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_publish,container,false)
    }
}