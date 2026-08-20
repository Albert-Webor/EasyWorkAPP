package com.shaun.easywork.ui.screen.english

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shaun.easywork.data.viewmodel.WordViewModel

@Composable
fun StudyScreen(
    viewModel: WordViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        // 返回按钮
        Button(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Text("← 返回")
        }

        // 单词内容
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = viewModel.word?.word ?: "加载中...",
                fontSize = 45.sp
            )

            Text(
                text = viewModel.word?.phonetic ?: "",
                fontSize = 24.sp
            )

            Text(
                text = viewModel.word?.part_of_speech ?: "",
                fontSize = 20.sp
            )

            Text(
                text = viewModel.word?.meaning ?: "",
                fontSize = 30.sp
            )
        }

        if (viewModel.message != null) {
            Text(
                text = viewModel.message!!,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                fontSize = 15.sp
            )
        }

        // 上一个 / 下一个
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                onClick = {
                    viewModel.previousWord()
                }
            ) {
                Text("上一个")
            }

            Button(
                onClick = {
                    viewModel.nextWord()
                }
            ) {
                Text("下一个")
            }
        }
    }
}