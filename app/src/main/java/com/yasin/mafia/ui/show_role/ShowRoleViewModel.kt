package com.yasin.mafia.ui.show_role

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yasin.mafia.DataBase.Person
import com.yasin.mafia.DataBase.Character
import com.yasin.mafia.DataBase.PersonDao
import com.yasin.mafia.R
import com.yasin.mafia.ui.start.ConfigDialog
import kotlinx.coroutines.*
import java.util.*

class ShowRoleViewModel(val data: PersonDao, application: Application) :
    AndroidViewModel(application) {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private var mCharacters = mutableListOf<Character>()
    private var cCharacters = mutableListOf<Character>()
    private var persons = listOf<Person>()
    private var _Cards = MutableLiveData<List<Card>>()

    init {
        initList(application)
    }

    fun initList(application: Application) {
        uiScope.launch {
            _Cards.value = withContext(Dispatchers.IO) {
                mCharacters = data.getwithSelectedAndSide(true, 1)
                cCharacters = data.getwithSelectedAndSide(true, 2)
                persons = data.getwithIsChecked(true)

                if (mCharacters.size + cCharacters.size != persons.size) {
                    for (i in 1..ConfigDialog.Mafia - mCharacters.size) {

                        mCharacters.add(
                            Character(
                                role = "مافیا",
                                image = "mafia",
                                discription = application.resources.getString(
                                    R.string.Mafia_Description
                                ), side = 1
                            )
                        )
                    }
                    for (i in 1..ConfigDialog.Citizen - cCharacters.size) {
                        mCharacters.add(
                            Character(
                                role = "شهروند",
                                image = "police",
                                discription = application.resources.getString(
                                    R.string.Citizen_Description
                                ), side = 2
                            )
                        )
                    }
                }


                val a = mutableListOf<Card>()
                if (mCharacters.size + cCharacters.size == persons.size) {
                    val b = (mCharacters + cCharacters).shuffled()
                    val c = persons.shuffled()
                    for (i in 0 until c.size) {
                        a.add(
                            Card(
                                c[i].name,
                                b[i]
                            )
                        )
                    }
                }

                a

            }!!
        }
    }

    val Cards: LiveData<List<Card>> = _Cards


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
