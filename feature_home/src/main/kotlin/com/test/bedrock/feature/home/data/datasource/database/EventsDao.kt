package com.test.bedrock.feature.home.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.bedrock.feature.home.data.datasource.database.model.EventEntityModel

@Dao
internal interface EventsDao {
    @Query("SELECT * FROM events")
    suspend fun getAllEvents(): List<EventEntityModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntityModel>)
}
