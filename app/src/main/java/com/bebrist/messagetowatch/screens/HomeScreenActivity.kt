package com.bebrist.messagetowatch.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bebrist.messagetowatch.R
import com.bebrist.messagetowatch.general.Constants

class HomeScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        Log.e(Constants.MAIN_LOG_TAG, "Test")
    }
}