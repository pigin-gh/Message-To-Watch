package com.bebrist.messagetowatch.screens

import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bebrist.messagetowatch.databinding.ActivityHomeScreenBinding
import com.bebrist.messagetowatch.factories.MainFactory
import com.bebrist.messagetowatch.general.Constants
import com.bebrist.messagetowatch.models.MainViewModel

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var mainViewModel: MainViewModel
    var currentText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e(Constants.MAIN_LOG_TAG, "Test")

        mainViewModel = ViewModelProvider(this, MainFactory(application))
            .get(MainViewModel::class.java)

        val savedText = savedInstanceState?.getString(Constants.SAVE_STATE_KEY)
        binding.textField.setText(savedText)
    }

    override fun onStart() {
        super.onStart()

        binding.textField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                return
            }

            override fun afterTextChanged(editable: Editable?) {
                currentText = editable.toString()
            }
        })

        binding.sendButton.setOnClickListener {
            if (currentText != "") {
                mainViewModel.sendNotification(currentText)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString(Constants.SAVE_STATE_KEY, currentText)
    }
}