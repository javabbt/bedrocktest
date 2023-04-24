package com.test.bedrock.feature.home.domain.usecase

import com.test.bedrock.base.domain.result.Result
import com.test.bedrock.feature.home.domain.model.Event
import com.test.bedrock.feature.home.domain.repository.EventRepository

internal class GetEventInfoUseCase(
    private val eventRepository: EventRepository,
) {
    suspend operator fun invoke(
        name: String,
    ): Result<Event> = eventRepository.getEventInfo(name = name)
}
