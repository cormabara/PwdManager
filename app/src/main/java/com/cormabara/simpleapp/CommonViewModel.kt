package com.cormabara.simpleapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommonViewModel : ViewModel()
{
    // This list will keep all values for Your ListView
    private val _list: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val list: LiveData<ArrayList<String>>
        get() = _list

    init
    {
        _list.value = ArrayList()
    }

    // function which will add new values to Your list
    fun addNewValue()
    {
        _list.value!!.add((0..100).random().toString())
        _list.value = _list.value //this will notify observers
    }
}