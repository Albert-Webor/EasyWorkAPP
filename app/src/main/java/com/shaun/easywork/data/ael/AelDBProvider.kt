package com.shaun.easywork.data.ael

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

object AelDBProvider {

    @Volatile
    private var INSTANCE: AelDB? = null

    fun getDatabase(context: Context): AelDB {
        return INSTANCE ?: synchronized(this) {

            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AelDB::class.java,
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