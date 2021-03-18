
package com.yasin.mafia.DataBase
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Person::class,Character::class], version = 16, exportSchema = false)
abstract class PersonDataBase : RoomDatabase() {


    abstract val personDao: PersonDao


    companion object {

        @Volatile
        private var INSTANCE: PersonDataBase? = null

        fun getInstance(context: Context): PersonDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PersonDataBase::class.java,
                        "Person"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
