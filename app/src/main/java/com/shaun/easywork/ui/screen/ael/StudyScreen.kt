package com.shaun.easywork.ui.screen.ael

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
import com.shaun.easywork.ui.screen.ael.style.StudyScreenStyle
import com.shaun.easywork.viewmodel.ael.AelViewModel

@Composable
fun StudyScreen(
    viewModel: AelViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(StudyScreenStyle.ScreenPadding)
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
            verticalArrangement = Arrangement.spacedBy(
                StudyScreenStyle.ContentSpacing
            )
        ) {

            Text(
                text = viewModel.word?.word ?: "加载中...",
                fontSize = StudyScreenStyle.WordFontSize
            )

            Text(
                text = viewModel.word?.phonetic ?: "",
                fontSize = StudyScreenStyle.PhoneticFontSize
            )

            Text(
                text = viewModel.word?.part_of_speech ?: "",
                fontSize = StudyScreenStyle.PartOfSpeechFontSize
            )

            Text(
                text = viewModel.word?.meaning ?: "",
                fontSize = StudyScreenStyle.MeaningFontSize
            )
        }

        // Message
        if (viewModel.message != null) {
            Text(
                text = viewModel.message!!,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(StudyScreenStyle.MessagePadding),
                fontSize = StudyScreenStyle.MessageFontSize
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