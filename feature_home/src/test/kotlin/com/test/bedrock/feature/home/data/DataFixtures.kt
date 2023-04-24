package com.test.bedrock.feature.home.data

import com.test.bedrock.feature.home.data.datasource.api.model.EventModel
import com.test.bedrock.feature.home.data.datasource.database.model.EventEntityModel

object DataFixtures {

    internal fun getEventsApiModel() = listOf(
        getEventApiModel("1"),
    )

    internal fun getEventsEntityModels() = listOf(
        getEventEntityModel(1),
        getEventEntityModel(2),
    )

    internal fun getEventApiModel(
        name: String,
    ): EventModel = EventModel(
        name = name,
        imageUrl = "image",
    )

    private fun getEventEntityModel(
        id: Int,
    ): EventEntityModel = EventEntityModel(
        id,
        url = "https://duckduckgo.com/?q=urbanitas",
        name = "test",
    )

    object ApiResponse {
        internal fun getEvents() = getEventsApiModel()
    }
}
