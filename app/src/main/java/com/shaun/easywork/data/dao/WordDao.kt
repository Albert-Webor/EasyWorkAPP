package com.shaun.easywork.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.shaun.easywork.data.entity.WordEntity
import com.shaun.easywork.data.entity.WordMeaningEntity
import com.shaun.easywork.data.entity.WordMeaningExampleEntity
/*
备注: 访问数据库的对象(执行SQL的对象)

*/

@Dao
interface WordDao {

    // =========================
    // ael_word
    // =========================

    /**
     * 查询所有单词
     */
    @Query("SELECT * FROM ael_word ORDER BY word")
    suspend fun getAllWords(): List<WordEntity>

    /**
     * 根据单词查询基础信息
     */
    @Query("SELECT * FROM ael_word WHERE word = :word")
    suspend fun getWord(word: String): WordEntity?

    /**
     * 查询第一个单词
     */
    @Query("SELECT * FROM ael_word ORDER BY word LIMIT 1")
    suspend fun getFirstWord(): WordEntity?

    // =========================
    // ael_word_meaning
    // =========================
    /**
     * 查询某个单词的所有含义
     */
    @Query("""
        SELECT *
        FROM ael_word_meaning
        WHERE word = :word
        ORDER BY seq_num
    """)
    suspend fun getWordMeanings(word: String): List<WordMeaningEntity>

    /**
     * 查询某个单词的指定含义
     */
    @Query("""
        SELECT *
        FROM ael_word_meaning
        WHERE word = :word
          AND seq_num = :seqNum
    """)
    suspend fun getWordMeaning(
        word: String,
        seqNum: Int
    ): WordMeaningEntity?

    // =========================
    // ael_word_meaning_example
    // =========================
    /**
     * 查询某个单词、某个含义对应的所有例句
     */
    @Query("""
        SELECT *
        FROM ael_word_meaning_example
        WHERE word = :word
          AND seq_num = :seqNum
        ORDER BY id
    """)
    suspend fun getMeaningExamples(
        word: String,
        seqNum: Int
    ): List<WordMeaningExampleEntity>

    /**
     * 查询某个单词的所有例句
     */
    @Query("""
        SELECT *
        FROM ael_word_meaning_example
        WHERE word = :word
        ORDER BY seq_num, id
    """)
    suspend fun getWordExamples(
        word: String
    ): List<WordMeaningExampleEntity>

}