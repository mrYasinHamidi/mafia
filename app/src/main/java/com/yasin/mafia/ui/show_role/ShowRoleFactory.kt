package com.yasin.mafia.ui.show_role

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yasin.mafia.DataBase.PersonDao
import java.lang.IllegalArgumentException

class ShowRoleFactory(
    private val data: PersonDao,
    private val application: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @file:Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(ShowRoleViewModel::class.java)) {
            return ShowRoleViewModel(data, application) as T
        }
        throw IllegalArgumentException("start view model factory")
    }
}