package com.shaun.easywork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shaun.easywork.data.database.AmericanEnglishDatabaseProvider
import com.shaun.easywork.data.viewmodel.WordViewModel
import com.shaun.easywork.data.viewmodel.WordViewModelFactory
import com.shaun.easywork.ui.screen.HomeScreen
import com.shaun.easywork.ui.screen.english.EnglishHomeScreen
import com.shaun.easywork.ui.screen.english.StudyScreen
import com.shaun.easywork.ui.theme.EasyWorkTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            EasyWorkTheme {

                var currentScreen by remember {
                    mutableStateOf("home")
                }

                when (currentScreen) {

                    "home" -> {
                        HomeScreen(
                            onEnglishClick = {
                                currentScreen = "english"
                            }
                        )
                    }

                    "english" -> {
                        EnglishHomeScreen(
                            onStudyClick = {
                                currentScreen = "study"
                            },
                            onSearchClick = {
                                // 下一步实现
                            }
                        )
                    }
                    "study" -> {

                        val database =
                            AmericanEnglishDatabaseProvider.getDatabase(this)

                        val wordDao = database.wordDao()

                        val factory =
                            WordViewModelFactory(wordDao)

                        val viewModel: WordViewModel =
                            viewModel(factory = factory)

                        LaunchedEffect(Unit) {
                            viewModel.loadWords()
                        }

                        StudyScreen(
                            viewModel = viewModel,
                            onBackClick = {
                                currentScreen = "english"
                            }
                        )
                    }
                }
            }
        }
    }
}