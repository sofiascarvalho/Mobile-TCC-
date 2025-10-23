package com.example.analyticai.service

import android.content.Context
import android.content.SharedPreferences

class AuthPreferences (context: Context){
    private val PREFS_NAME = "app_auth_prefs"
    private val KEY_ALUNO_ID = "id_aluno"

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveLoginData(idAluno: Int){
        sharedPrefs.edit().apply(){
            putInt(KEY_ALUNO_ID, idAluno)
            apply()
        }
    }

    ////
}