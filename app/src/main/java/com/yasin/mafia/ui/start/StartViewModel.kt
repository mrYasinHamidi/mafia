package com.yasin.mafia.ui.start

import android.app.Application
import androidx.lifecycle.*
import com.yasin.mafia.DataBase.Person
import com.yasin.mafia.DataBase.PersonDao
import kotlinx.coroutines.*

class StartViewModel(val data: PersonDao, application: Application) :
    AndroidViewModel(application) {

    var job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    val Names = data.getwithIsCheckedL(true)

    val persons = data.getAllPerson()

    var mafiaReminder = true

    fun insert(name: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val person = Person(name = name)
                data.insert(person)
            }
        }

    }

    fun delete(person: Person) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                data.delete(person)
            }
        }

    }

    fun update(person: Person) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                data.update(person)
            }
        }
    }


    fun setAllCitizenCharactertoFalse() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                for (i in data.getwithSelectedAndSide(true, 2)) {
                    i.selected = false
                    data.update(i)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


}
