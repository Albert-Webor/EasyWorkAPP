package com.shaun.easywork.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

import com.shaun.easywork.data.ael.AelDBProvider
import com.shaun.easywork.ui.screen.HomeScreen
import com.shaun.easywork.ui.screen.ael.EnglishHomeScreen
import com.shaun.easywork.ui.screen.ael.StudyScreen
import com.shaun.easywork.ui.screen.notes.NotesHomeScreen
import com.shaun.easywork.viewmodel.ael.AelViewModel
import com.shaun.easywork.viewmodel.ael.AelViewModelFactory

@Composable
fun AppNavigation() {

    var currentScreen by remember {
        mutableStateOf("home")
    }

    val context = LocalContext.current

    when (currentScreen) {
        "home" -> {
            HomeScreen(
                onAelClick = { currentScreen = "english"  },
                onNotesClick = { currentScreen = "notes" }
            )
        }

        "english" -> {

            EnglishHomeScreen(
                onStudyClick = {
                    currentScreen = "study"
                },
                onSearchClick = {
                    // TODO: 搜索单词
                }
            )
        }

        "study" -> {

            val database =
                AelDBProvider
                    .getDatabase(context)

            val wordDao =
                database.wordDao()

            val factory =
                AelViewModelFactory(wordDao)

            val viewModel: AelViewModel =
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

        "notes" -> {
            NotesHomeScreen(
                onBackClick = {
                    currentScreen = "home"
                }
            )
        }
    }
}