package com.example.analyticai.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RecursosViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecursosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecursosViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
