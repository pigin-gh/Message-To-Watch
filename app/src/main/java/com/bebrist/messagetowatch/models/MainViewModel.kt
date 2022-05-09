package com.bebrist.messagetowatch.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bebrist.messagetowatch.general.Constants

class MainViewModel(application: Application) : AndroidViewModel(application) {


    init {

    }

    fun sendNotification(text: String) {
        Log.e(Constants.MAIN_LOG_TAG, text)
    }

}