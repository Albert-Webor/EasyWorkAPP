package com.shaun.easywork.data.ael

import androidx.room.Entity
import androidx.room.PrimaryKey
/*
* 备注 : 数据库表对象
* 规范 : 末尾一定是: Entity 结尾
*
*
* */

/**
 * 单词表：存储单词的基础信息
 */
@Entity(tableName = "ael_word")
data class WordEntity(
    @PrimaryKey
    val word: String,

    val phonetic: String?,

    val meaning: String,

    val part_of_speech: String?,

    val level: String?
)

/**
 * 单词含义表：存储一个单词的其他含义
 */
@Entity(
    tableName = "ael_word_meaning",
    primaryKeys = ["word", "seq_num"]
)
data class WordMeaningEntity(
    val word: String,

    val seq_num: Int,

    val meaning: String,

    val part_of_speech: String?
)

/**
 * 单词含义例句表：存储每个具体含义对应的例句
 */
@Entity(tableName = "ael_word_meaning_example")
data class WordMeaningExampleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val word: String,
    val seq_num: Int,
    val example: String,
    val example_translation: String?
)