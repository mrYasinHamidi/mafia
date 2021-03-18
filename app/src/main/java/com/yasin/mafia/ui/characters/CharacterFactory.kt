package com.yasin.mafia.ui.characters

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yasin.mafia.DataBase.PersonDao
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class CharacterFactory(
    private val isMaafia: Boolean,
    private val data: PersonDao,
    private val application: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            return CharacterViewModel(isMaafia,data, application) as T
        }
        throw IllegalArgumentException("characterViewModel")
    }
}