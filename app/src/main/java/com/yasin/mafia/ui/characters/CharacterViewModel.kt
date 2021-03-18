package com.yasin.mafia.ui.characters

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.yasin.mafia.DataBase.PersonDao
import com.yasin.mafia.ui.start.ConfigDialog
import com.yasin.mafia.DataBase.Character
import kotlinx.coroutines.*


class CharacterViewModel(isMafia: Boolean, val data: PersonDao, application: Application) :
    AndroidViewModel(application) {
    private val Mafia: Boolean


    init {
        Mafia = isMafia
    }

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val Characters = data.getWithSideL(if (Mafia) 1 else 2)
    val updateCharacter: MutableList<Character> = mutableListOf()

    var Limit = if (Mafia) ConfigDialog.Mafia else ConfigDialog.Citizen
    fun update(characters: List<Character>) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                data.update(characters)
            }
        }

    }

    fun setAlltoFalse() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                for (i in data.getwithSelectedAndSide(true, 1)) {
                    i.selected = false
                    data.update(i)
                }
            }
        }
    }


//    fun getListOfRoles(side: Int): Array<String> {
//        val Roles = mutableListOf<String>()
//        when (side) {
//            1 -> {
//                for (i in mSelected)
//                    Roles.add(i.role)
//                val numberOfSimpleMafia =
//                    ConfigDialog.Mafia - Roles.size // this for set other players to simple mafia
//                for (i in 1..numberOfSimpleMafia) {
//                    Roles.add("مافیا")
//                }
//
//            }
//            2 -> {
//
//                for (i in cSelected)
//                    Roles.add(i.role)
//
//                val numberOfSimpleCitizen =
//                    ConfigDialog.Citizen - cSelected.size // this for set other players to simple mafia
//                for (i in 1..numberOfSimpleCitizen) {
//                    Roles.add("شهروند")
//                }
//            }
//            else -> {
//                Toast.makeText(getApplication(), "طرف اشتباهی رو اتخاب کردی", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//        return Roles.toTypedArray()
//    }


}