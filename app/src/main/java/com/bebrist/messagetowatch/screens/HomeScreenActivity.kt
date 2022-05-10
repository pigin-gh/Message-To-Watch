package com.bebrist.messagetowatch.screens

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bebrist.messagetowatch.R
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

        // Установка ViewBinding
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // Соединение с ViewModel
        mainViewModel = ViewModelProvider(this, MainFactory(application))
            .get(MainViewModel::class.java)

        // Получение последнего текста после поворота экрана
        val savedText = savedInstanceState?.getString(Constants.SAVE_STATE_KEY)
        binding.textField.setText(savedText)

        mainViewModel.createNotificationChannel()
    }

    override fun onStart() {
        super.onStart()

        // Установка слушателя изменения текста в поле
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

        // Слушатель нажатия на кнопку отправки
        binding.sendButton.setOnClickListener {

            // Фильтр содержания и длины текста
            if (currentText != "" && currentText.length <= 90) {
                mainViewModel.sendNotification(currentText) // Вызов функции из ViewModel (отправка пуша)
                binding.textFieldLayout.error = null
            } else if (currentText.length > 90) {
                binding.textFieldLayout.error = getString(R.string.length_error)
            } else {
                binding.textFieldLayout.error = getString(R.string.error)
            }
        }
    }

    // Установка записи текста в сохраненное состояние экземпляра
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString(Constants.SAVE_STATE_KEY, currentText)
    }
}