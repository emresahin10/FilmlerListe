package com.denbase.filmlerliste.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PrivateSharedPreferences {
    companion object{

        private val  TIME = "time"
        private var sp : SharedPreferences? = null

        @Volatile private var instance : PrivateSharedPreferences? = null

        private val lock = Any()
        operator fun invoke(context: Context): PrivateSharedPreferences = instance ?: synchronized(lock){

            instance ?: doPrivateSharedPreferences(context).also {

                instance = it
            }
        }

        private fun doPrivateSharedPreferences(context: Context): PrivateSharedPreferences {
            sp = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return PrivateSharedPreferences()
        }
    }

    fun saveTime(time: Long){
        sp?.edit(commit = true){
            putLong(TIME, time)
        }
    }

    fun getTime() = sp?.getLong(TIME, 0)
}