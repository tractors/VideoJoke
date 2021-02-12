package com.will.videojoke.ui.sofa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SofaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is sofa Fragment"
    }
    val text: LiveData<String> = _text
}