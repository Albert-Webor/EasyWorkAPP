package com.shaun.easywork.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shaun.easywork.data.dao.WordDao
import com.shaun.easywork.data.entity.WordEntity
import com.shaun.easywork.data.entity.WordMeaningEntity
import com.shaun.easywork.data.entity.WordMeaningExampleEntity
/*
* 备注: 这个类代表的是Sqlite文件: AmericanEnglishLearning (一般一个数据库文件使用一个kt文件)
* 备注2: 这个类的功能就是告诉Room, 这个对应的数据库内有哪些表, 哪些Dao对象
* */

@Database(
    entities = [
        WordEntity::class,
        WordMeaningEntity::class,
        WordMeaningExampleEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AmericanEnglishDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao
}