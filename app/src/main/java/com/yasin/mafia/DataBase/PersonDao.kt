
package com.yasin.mafia.DataBase

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface PersonDao {

    @Insert
    fun insert(person: Person)

    @Query("SELECT * from Person WHERE personId = :key")
    fun get(key: Int): Person?

    @Query("DELETE FROM Person")
    fun clear()

    @Query("SELECT * FROM Person ORDER BY personId DESC")
    fun getAllPerson(): LiveData<List<Person>>

    @Update
    fun update(person: Person)

    @Query("SELECT * from Person WHERE isChecked = :bool")
    fun getwithIsCheckedL(bool: Boolean): LiveData<List<Person>>

    @Query("SELECT * from Person WHERE isChecked = :bool")
    fun getwithIsChecked(bool: Boolean): List<Person>

    @Delete
    fun delete(person: Person)


    //Character table
    @Insert
    fun insert(characters: List<Character>)

    @Query("SELECT * FROM Character")
    fun getAllCharacters(): List<Character>

    @Query("SELECT * from character WHERE side = :side")
    fun getWithSideL(side: Int): LiveData<List<Character>>

    @Query("SELECT * from character WHERE side = :side")
    fun getWithSide(side: Int): List<Character>

    @Query("SELECT * from Character WHERE selected = :selected AND side = :side")
    fun getwithSelectedAndSide(selected: Boolean, side: Int): MutableList<Character>

    @Update
    fun update(characters: List<Character>)

    @Update
    fun update(characters: Character)

}

