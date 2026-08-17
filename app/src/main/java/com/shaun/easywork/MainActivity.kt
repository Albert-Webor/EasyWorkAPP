package com.shaun.easywork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shaun.easywork.ui.theme.EasyWorkTheme
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shaun.easywork.data.database.AmericanEnglishDatabaseProvider
import com.shaun.easywork.data.viewmodel.WordViewModel
import com.shaun.easywork.data.viewmodel.WordViewModelFactory
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.sp
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val database = AmericanEnglishDatabaseProvider.getDatabase(this)
            val wordDao = database.wordDao()
            val factory = WordViewModelFactory(wordDao)
            val viewModel: WordViewModel = viewModel(factory = factory)
            LaunchedEffect(Unit) {
                viewModel.loadWords()
            }

            EasyWorkTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    WordQuestionScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun WordQuestionScreen(
    viewModel: WordViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        if (viewModel.message != null) {
            Text(
                text = viewModel.message!!,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                fontSize = 20.sp
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = viewModel.word?.word ?: "加载中...",
                fontSize = 60.sp
            )

            Text(
                text = viewModel.word?.meaning ?: "",
                fontSize = 30.sp
            )
        }

        // 左下角、右下角按钮
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(onClick = {viewModel.previousWord()}) {
                Text("上一个")
            }

            Button(onClick = {viewModel.nextWord()}) {
                Text("下一个")
            }
        }
    }
}