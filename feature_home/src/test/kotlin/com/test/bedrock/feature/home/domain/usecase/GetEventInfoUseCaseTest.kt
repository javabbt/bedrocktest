package com.test.bedrock.feature.home.domain.usecase

import com.test.bedrock.base.domain.result.Result
import com.test.bedrock.feature.home.data.repository.EventRepositoryImpl
import com.test.bedrock.feature.home.domain.model.Event
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

class GetEventInfoUseCaseTest {

    private val mockEventRepository: EventRepositoryImpl = mockk()

    private val cut = GetEventInfoUseCase(mockEventRepository)

    @Test
    fun `return event`() {
        // given
        val id = 1.toString()

        val event = mockk<Event>()
        coEvery { mockEventRepository.getEventInfo(id) } answers { Result.Success(event) }

        // when
        val actual = runBlocking { cut(id) }

        // then
        actual shouldBeEqualTo Result.Success(event)
    }
}
