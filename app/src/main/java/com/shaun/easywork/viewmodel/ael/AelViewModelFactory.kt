package com.shaun.easywork.viewmodel.ael
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shaun.easywork.data.ael.AelDao

class AelViewModelFactory(
    private val aelDao: AelDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AelViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AelViewModel(aelDao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}