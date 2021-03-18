package com.yasin.mafia.ui.start

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yasin.mafia.DataBase.PersonDao
import java.lang.IllegalArgumentException

class StartViewModelFactory(private val data: PersonDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @file:Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(StartViewModel::class.java)) {
            return StartViewModel(data,application) as T
        }
        throw IllegalArgumentException("start view model factory")
    }
}