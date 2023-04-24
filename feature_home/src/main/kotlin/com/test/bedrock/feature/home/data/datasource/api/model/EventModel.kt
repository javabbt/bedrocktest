package com.test.bedrock.feature.home.data.datasource.api.model

import com.test.bedrock.feature.home.data.datasource.database.model.EventEntityModel
import com.test.bedrock.feature.home.domain.model.Event
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventModel(
    @SerialName("name")
    val name: String,
    @SerialName("image")
    val imageUrl: String,
)

internal fun EventModel.toEntityModel() = EventEntityModel(
    name = this.name,
    url = this.imageUrl,
)

internal fun EventModel.toDomainModel() = Event(
    name = this.name,
    imageUrl = this.imageUrl.replace("http", "https"),
)
