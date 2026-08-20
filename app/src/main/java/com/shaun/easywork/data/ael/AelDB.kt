package com.shaun.easywork.data.ael

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        WordEntity::class,
        WordMeaningEntity::class,
        WordMeaningExampleEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AelDB : RoomDatabase() {

    abstract fun wordDao(): AelDao
}