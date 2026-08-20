package com.shaun.easywork.ui.screen.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NotesHomeScreen(
    onBackClick: () -> Unit
) {

    val context = LocalContext.current

    // ==================================================
    // 笔记目录
    // ==================================================
    val notesDirectory = remember {

        File(
            context.filesDir,
            "notes"
        ).apply {

            if (!exists()) {
                mkdirs()
            }
        }
    }

    // ==================================================
    // 读取笔记列表
    // 按最后修改时间倒序排列
    // ==================================================
    var notes by remember {

        mutableStateOf<List<String>>(
            notesDirectory
                .listFiles()
                ?.filter {
                    it.isFile && it.extension == "md"
                }
                ?.sortedByDescending {
                    it.lastModified()
                }
                ?.map {
                    it.name
                }
                ?: emptyList()
        )
    }

    // ==================================================
    // 新建笔记
    // ==================================================
    var showNewNoteDialog by remember {
        mutableStateOf(false)
    }

    var noteName by remember {
        mutableStateOf("")
    }

    // ==================================================
    // 删除笔记
    // ==================================================
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    var deleteFileName by remember {
        mutableStateOf("")
    }

    // ==================================================
    // 页面
    // ==================================================
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // ==================================================
        // 页面内容
        // ==================================================
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            // 返回按钮
            Button(
                onClick = onBackClick
            ) {
                Text("← 返回")
            }

            // 页面标题
            Text(
                text = "我的笔记",
                fontSize = 30.sp,
                modifier = Modifier.padding(
                    top = 24.dp,
                    bottom = 24.dp
                )
            )

            // ==================================================
            // 笔记列表
            // ==================================================
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(
                    items = notes,
                    key = { fileName -> fileName }
                ) { fileName ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),

                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // ==================================================
                        // 笔记名称 + 最后修改时间
                        // ==================================================
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            // 笔记名称
                            Text(
                                text = fileName.substringBeforeLast(".md"),
                                fontSize = 20.sp
                            )

                            // 最后修改时间
                            Text(
                                text = formatLastModified(
                                    File(
                                        notesDirectory,
                                        fileName
                                    ).lastModified()
                                ),
                                fontSize = 12.sp,
                                modifier = Modifier.align(
                                    Alignment.End
                                )
                            )
                        }

                        // ==================================================
                        // 删除按钮
                        // ==================================================
                        IconButton(
                            onClick = {

                                deleteFileName = fileName
                                showDeleteDialog = true
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "删除笔记",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }

        // ==================================================
        // 右侧垂直居中的 + 按钮
        // ==================================================
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {

            SmallFloatingActionButton(
                onClick = {

                    noteName = ""
                    showNewNoteDialog = true
                },

                modifier = Modifier.padding(
                    end = 16.dp
                ),

                shape = CircleShape
            ) {

                Text(
                    text = "+",
                    fontSize = 20.sp
                )
            }
        }
    }

    // ==================================================
    // 新建笔记弹窗
    // ==================================================
    if (showNewNoteDialog) {

        AlertDialog(

            onDismissRequest = {
                showNewNoteDialog = false
            },

            title = {
                Text("新建笔记")
            },

            text = {

                OutlinedTextField(

                    value = noteName,

                    onValueChange = {
                        noteName = it
                    },

                    label = {
                        Text("请输入笔记本名称")
                    },

                    singleLine = true
                )
            },

            // ==================================================
            // 新建
            // ==================================================
            confirmButton = {

                TextButton(

                    onClick = {

                        val name = noteName.trim()

                        if (name.isNotEmpty()) {

                            val file = File(
                                notesDirectory,
                                "$name.md"
                            )

                            // 文件不存在才创建
                            if (!file.exists()) {

                                file.writeText("")

                                // ==================================================
                                // 刷新列表
                                // 最近修改的排在最前面
                                // ==================================================
                                notes =
                                    notesDirectory
                                        .listFiles()
                                        ?.filter {
                                            it.isFile &&
                                                    it.extension == "md"
                                        }
                                        ?.sortedByDescending {
                                            it.lastModified()
                                        }
                                        ?.map {
                                            it.name
                                        }
                                        ?: emptyList()
                            }

                            // 关闭弹窗
                            showNewNoteDialog = false
                        }
                    }
                ) {

                    Text("新建")
                }
            },

            // ==================================================
            // 取消
            // ==================================================
            dismissButton = {

                TextButton(

                    onClick = {
                        showNewNoteDialog = false
                    }
                ) {

                    Text("取消")
                }
            }
        )
    }

    // ==================================================
    // 删除确认弹窗
    // ==================================================
    if (showDeleteDialog) {

        AlertDialog(

            onDismissRequest = {
                showDeleteDialog = false
            },

            title = {
                Text("确认删除?")
            },

            text = {

                Text(
                    "确定要删除「${
                        deleteFileName.substringBeforeLast(".md")
                    }」吗？"
                )
            },

            // ==================================================
            // 确认删除
            // ==================================================
            confirmButton = {

                TextButton(

                    onClick = {

                        val file = File(
                            notesDirectory,
                            deleteFileName
                        )

                        // 删除文件
                        if (file.exists()) {
                            file.delete()
                        }

                        // ==================================================
                        // 刷新列表
                        // 最近修改的排在最前面
                        // ==================================================
                        notes =
                            notesDirectory
                                .listFiles()
                                ?.filter {
                                    it.isFile &&
                                            it.extension == "md"
                                }
                                ?.sortedByDescending {
                                    it.lastModified()
                                }
                                ?.map {
                                    it.name
                                }
                                ?: emptyList()

                        showDeleteDialog = false
                    }
                ) {

                    Text(
                        text = "确认",
                        color = Color.Red
                    )
                }
            },

            // ==================================================
            // 取消删除
            // ==================================================
            dismissButton = {

                TextButton(

                    onClick = {
                        showDeleteDialog = false
                    }
                ) {

                    Text("取消")
                }
            }
        )
    }
}


// ======================================================
// 格式化最后修改时间
// ======================================================
fun formatLastModified(
    timestamp: Long
): String {

    if (timestamp == 0L) {
        return ""
    }

    val formatter = SimpleDateFormat(
        "yyyy-MM-dd HH:mm",
        Locale.getDefault()
    )

    return formatter.format(
        Date(timestamp)
    )
}