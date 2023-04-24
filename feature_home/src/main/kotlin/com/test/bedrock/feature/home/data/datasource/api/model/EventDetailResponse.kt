package com.test.bedrock.feature.home.data.datasource.api.model

import com.test.bedrock.feature.home.domain.model.Event

@kotlinx.serialization.Serializable
data class EventDetailResponse(
    val image: String,
    val name: String,
    val text: String,
)

fun EventDetailResponse.toEvent() = Event(
    name = this.text,
    imageUrl = this.image.replace("http", "https"),
)
