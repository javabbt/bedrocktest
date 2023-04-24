package com.test.bedrock.feature.home.domain.usecase

import com.test.bedrock.base.domain.result.Result
import com.test.bedrock.feature.home.data.repository.EventRepositoryImpl
import com.test.bedrock.feature.home.domain.DomainFixtures
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

class GetEventsListUseCaseTest {

    private val mockEventRepository: EventRepositoryImpl = mockk()

    private val cut = GetEventsListUseCase(mockEventRepository)

    @Test
    fun `return list of events`() {
        // given
        val events = listOf(DomainFixtures.getEvent(), DomainFixtures.getEvent())
        coEvery { mockEventRepository.searchEvents() } returns Result.Success(events)

        // when
        val actual = runBlocking { cut() }

        // then
        actual shouldBeEqualTo Result.Success(events)
    }

    @Test
    fun `WHEN onEnter is called with no value then the default query search term is empty`() = runBlocking {
        // given
        val events = listOf(DomainFixtures.getEvent(), DomainFixtures.getEvent())
        coEvery { mockEventRepository.searchEvents() } returns Result.Success(events)

        cut()

        coVerify { mockEventRepository.searchEvents() }
    }

    @Test
    fun `return error when repository throws an exception`() {
        // given
        val resultFailure = mockk<Result.Failure>()
        coEvery { mockEventRepository.searchEvents() } returns resultFailure

        // when
        val actual = runBlocking { cut() }

        // then
        actual shouldBeEqualTo resultFailure
    }
}
