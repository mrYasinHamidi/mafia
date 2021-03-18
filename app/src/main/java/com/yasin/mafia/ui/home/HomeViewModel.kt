package com.yasin.mafia.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.yasin.mafia.DataBase.PersonDao
import com.yasin.mafia.DataBase.Character
import com.yasin.mafia.R
import kotlinx.coroutines.*

class HomeViewModel(
    val data: PersonDao,
    application: Application
) : AndroidViewModel(application) {

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    fun initializeDataBase() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                if (data.getAllCharacters().isNullOrEmpty()) {
                    data.insert(insertMafia(getApplication()))
                    data.insert(insertCitizen(getApplication()))
                }
            }
        }
    }

    fun setAllPersonToFalse() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                for (i in data.getwithIsChecked(true)) {
                    i.isChecked = false
                    data.update(i)
                }
            }
        }
    }


    private fun insertMafia(resources: Application): MutableList<Character> {
        val Characters = mutableListOf<Character>()
        Characters.add(
            Character(
                role = "مافیا",
                image = "mafia",
                discription = resources.getString(R.string.Mafia_Description),
                side = 1

            )
        )
        Characters.add(
            Character(
                role = "گادفادر",
                image = "godfather",
                discription = resources.getString(R.string.Godfather_Description),
                side = 1


            )
        )
        Characters.add(
            Character(
                role = "ناتاشا",
                image = "natasha",
                discription = resources.getString(R.string.Natasha_Description),
                side = 1


            )
        )
        Characters.add(
            Character(
                role = "وکیل",
                image = "lawyer",
                discription = resources.getString(R.string.Lawyer_Description),
                side = 1

            )
        )
        Characters.add(
            Character(
                role = "تروریست",
                image = "terrorist",
                discription = resources.getString(R.string.Terrorist_Description),
                side = 1

            )
        )
        Characters.add(
            Character(
                role = "مرد قوی",
                image = "strong_man",
                discription = resources.getString(R.string.Strongman_Description),
                side = 1

            )
        )
        Characters.add(
            Character(
                role = "تهمت زن",
                image = "slander",
                discription = resources.getString(R.string.Slander_Description),
                side = 1

            )
        )
        Characters.add(
            Character(
                role = "افسونگر",
                image = "wizard",
                discription = resources.getString(R.string.Wizard_Description),
                side = 1

            )
        )
        Characters.add(
            Character(
                role = "دزد",
                image = "thief",
                discription = resources.getString(R.string.Thief_Description),
                side = 1

            )
        )
        Characters.add(
            Character(
                role = "جاسوس",
                image = "spy",
                discription = resources.getString(R.string.Spy_Description),
                side = 1

            )
        )
        return Characters
    }

    private fun insertCitizen(resources: Application): MutableList<Character> {
        val Characters = mutableListOf<Character>()
        Characters.add(
            Character(
                role = "شهروند",
                image = "police",
                discription = resources.getString(R.string.Citizen_Description),
                side = 2

            )
        )
        Characters.add(
            Character(
                role = "دکتر",
                image = "doctor",
                discription = resources.getString(R.string.Doctor_Description),
                side = 2
            )
        )
        Characters.add(
            Character(
                role = "کشیش",
                image = "prist",
                discription = resources.getString(R.string.Prist_Description),
                side = 2


            )
        )
        Characters.add(
            Character(
                role = "قاضی",
                image = "judge",
                discription = resources.getString(R.string.Judge_Description),
                side = 2

            )
        )
        Characters.add(
            Character(
                role = "باکره",
                image = "virgin",
                discription = resources.getString(R.string.Vrigin_Description),
                side = 2


            )
        )
        Characters.add(
            Character(
                role = "کارآگاه",
                image = "detective",
                discription = resources.getString(R.string.Detactive_Description),
                side = 2


            )
        )
        Characters.add(
            Character(
                role = "تک تیرانداز",
                image = "sniper",
                discription = resources.getString(R.string.Sniper_Description),
                side = 2


            )
        )
        Characters.add(
            Character(
                role = "فراماسون",
                image = "freemasone",
                discription = resources.getString(R.string.Freemasone_Description),
                side = 2


            )
        )
        Characters.add(
            Character(
                role = "بمبر",
                image = "bomber",
                discription = resources.getString(R.string.Bomber_Description),
                side = 2


            )
        )
        Characters.add(
            Character(
                role = "کابوی",
                image = "cowboy",
                discription = resources.getString(R.string.Cowboy_Description),
                side = 2


            )
        )
        Characters.add(
            Character(
                role = "ساقی",
                image = "dealer",
                discription = resources.getString(R.string.Dealer_Description),
                side = 2


            )
        )
        Characters.add(
            Character(
                role = "رویین تن",
                image = "revengeous",
                discription = resources.getString(R.string.Revengeous_Description),
                side = 2


            )
        )
        Characters.add(
            Character(
                role = "محافظ",
                image = "guard",
                discription = resources.getString(R.string.Gaurd_Description),
                side = 2


            )
        )
        Characters.add(
            Character(
                role = "تفنگ دار",
                image = "musketeer",
                discription = resources.getString(R.string.Musketeer_Description),
                side = 2


            )
        )
        return Characters
    }

}