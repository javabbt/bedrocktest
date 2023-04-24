package com.test.bedrock.feature.home.domain.usecase

import com.test.bedrock.base.domain.result.Result
import com.test.bedrock.feature.home.domain.model.Event
import com.test.bedrock.feature.home.domain.repository.EventRepository

internal class GetEventsListUseCase(
    private val eventRepository: EventRepository,
) {
    suspend operator fun invoke(): Result<List<Event>> =
        eventRepository.searchEvents()
}
