package com.test.bedrock.feature.home.domain.repository

import com.test.bedrock.base.domain.result.Result
import com.test.bedrock.feature.home.domain.model.Event

internal interface EventRepository {
    suspend fun getEventInfo(name: String): Result<Event>
    suspend fun searchEvents(): Result<List<Event>>
}
