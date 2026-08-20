package com.shaun.easywork.viewmodel.ael

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.shaun.easywork.data.ael.AelDao
import com.shaun.easywork.data.ael.WordEntity
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class AelViewModel(
    private val aelDao: AelDao
) : ViewModel() {

    var word by mutableStateOf<WordEntity?>(null)
        private set

    //初始化一个空数组
    private var words: List<WordEntity> = emptyList()

    //当前单词所处位置
    private var currentIndex = 0

    var message by mutableStateOf<String?>(null)
        private set

    fun loadWords() {
        viewModelScope.launch {
            words = aelDao.getAllWords()

            if (words.isNotEmpty()) {
                currentIndex = 0
                word = words[currentIndex]
            }
        }
    }

    fun nextWord() {
        println("NEXT BUTTON CLICKED")
        println("currentIndex = $currentIndex")
        println("words.size = ${words.size}")
        if (currentIndex < words.lastIndex) {
            currentIndex++
            word = words[currentIndex]
        }else {
            message = "已经是最后一个"
            viewModelScope.launch {
                delay(1000)
                message = null
            }
        }
    }

    fun previousWord() {
        if (currentIndex > 0) {
            currentIndex--
            word = words[currentIndex]
        }else {
            message = "已经是第一个"

            viewModelScope.launch {
                delay(1000)
                message = null
            }
        }
    }
}