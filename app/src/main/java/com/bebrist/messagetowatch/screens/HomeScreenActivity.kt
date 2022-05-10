package com.bebrist.messagetowatch.screens

import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bebrist.messagetowatch.databinding.ActivityHomeScreenBinding
import com.bebrist.messagetowatch.factories.MainFactory
import com.bebrist.messagetowatch.general.Constants
import com.bebrist.messagetowatch.models.MainViewModel

/**
 *      Простое приложение для Android 5.1 или выше для отправки небольших
 *      сообщений на smart-часы или фитнес браслеты.
 *      Можно использовать для отправки шпаргалок :)
 *      10.05.2022 - v1.0
 **/

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Установка ViewBinding
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // Соединение с ViewModel и фабрикой
        mainViewModel = ViewModelProvider(this, MainFactory(application))
            .get(MainViewModel::class.java)

        // Получение последнего текста после поворота экрана
        val savedText = savedInstanceState?.getString(Constants.SAVE_STATE_KEY)
        binding.textField.setText(savedText)

        // Создание канала уведомлений (функция из ViewModel)
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
                mainViewModel.currentText = editable.toString()
            }
        })

        // Слушатель нажатия на кнопку отправки
        binding.sendButton.setOnClickListener {
            mainViewModel.sendNotification()
        }
    }

    // Установка записи текста в сохраненное состояние экземпляра
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString(Constants.SAVE_STATE_KEY, mainViewModel.currentText)
    }
}