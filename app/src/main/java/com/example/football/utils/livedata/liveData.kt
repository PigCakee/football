package com.example.football.utils.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

fun <T> mutableLiveData(defaultValue: T? = null): MutableLiveData<T> {
    val data = MutableLiveData<T>()

    if (defaultValue != null) {
        data.value = defaultValue
    }

    return data
}
