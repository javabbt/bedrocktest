package com.test.bedrock.feature.home.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.bedrock.feature.home.data.datasource.database.model.EventEntityModel

@Database(entities = [EventEntityModel::class], version = 1, exportSchema = false)
internal abstract class EventDatabase : RoomDatabase() {
    abstract fun events(): EventsDao
}
