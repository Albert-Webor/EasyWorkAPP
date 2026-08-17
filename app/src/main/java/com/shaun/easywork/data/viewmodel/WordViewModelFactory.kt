package com.shaun.easywork.data.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shaun.easywork.data.dao.WordDao

class WordViewModelFactory(
    private val wordDao: WordDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(wordDao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}