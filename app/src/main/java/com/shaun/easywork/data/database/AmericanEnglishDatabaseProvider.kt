package com.shaun.easywork.data.database

import android.content.Context
import androidx.room.Room

/*
* 备注 : 负责提供数据库访问入口, 也是建议一个数据库单独一个DatabaseProvide类
* Provider 给你数据库，Database 给你 DAO，DAO 给你数据。‘
* Provider
   ↓
Database
   ↓
DAO
   ↓
Data
* */

object AmericanEnglishDatabaseProvider {

    @Volatile
    private var INSTANCE: AmericanEnglishDatabase? = null

    fun getDatabase(context: Context): AmericanEnglishDatabase {
        return INSTANCE ?: synchronized(this) {

            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AmericanEnglishDatabase::class.java,
                "AmericanEnglishLearning"
            )
                .createFromAsset("AmericanEnglishLearning")
                .build()
                .also {
                    INSTANCE = it
                }
        }
    }
}