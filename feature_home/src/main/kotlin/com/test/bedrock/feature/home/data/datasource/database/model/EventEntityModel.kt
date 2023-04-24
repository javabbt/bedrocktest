package com.test.bedrock.feature.home.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.bedrock.feature.home.domain.model.Event
import com.test.bedrock.feature.home.domain.model.EventDetail

@Entity(tableName = "events")
internal data class EventEntityModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val name: String,
    val url: String,
)

internal fun EventEntityModel.toDomainModel() =
    Event(
        this.url,
        this.name,
    )

internal fun EventEntityModel.toEventDetailDomainModel() =
    EventDetail(
        name = this.name,
        url = this.url,
    )
