package com.example.football.utils.livedata

import androidx.lifecycle.MutableLiveData

fun <T> mutableLiveData(defaultValue: T? = null): MutableLiveData<T> {
    val data = MutableLiveData<T>()

    if (defaultValue != null) {
        data.value = defaultValue
    }

    return data
}
