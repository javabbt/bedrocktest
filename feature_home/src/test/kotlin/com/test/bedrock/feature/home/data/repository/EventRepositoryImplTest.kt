package com.test.bedrock.feature.home.data.repository

import com.test.bedrock.base.data.retrofit.ApiResult
import com.test.bedrock.base.domain.result.Result
import com.test.bedrock.feature.home.data.DataFixtures
import com.test.bedrock.feature.home.data.datasource.api.model.EventModel
import com.test.bedrock.feature.home.data.datasource.api.model.toDomainModel
import com.test.bedrock.feature.home.data.datasource.api.service.EventsRetrofitService
import com.test.bedrock.feature.home.data.datasource.database.EventsDao
import com.test.bedrock.feature.home.data.datasource.database.model.toDomainModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import java.net.UnknownHostException

class EventRepositoryImplTest {

    private val mockService: EventsRetrofitService = mockk()

    private val mockEventsDao: EventsDao = mockk(relaxed = true)

    private val cut = EventRepositoryImpl(mockService, mockEventsDao)

    @Test
    fun `searchEvent handles api success and returns events`() {
        // given
        val events = DataFixtures.getEventsApiModel()

        coEvery { mockService.getEvents() } returns ApiResult.Success(
            DataFixtures.ApiResponse.getEvents(),
        )

        // when
        val actual = runBlocking { cut.searchEvents() }

        // then
        val eventsDomain = events.map { it.toDomainModel() }
        actual shouldBeEqualTo Result.Success(eventsDomain)
    }

    @Test
    fun `searchEvent handles api success and saves event in database`() {
        // given
        coEvery { mockService.getEvents() } returns ApiResult.Success(
            DataFixtures.ApiResponse.getEvents(),
        )

        // when
        runBlocking { cut.searchEvents() }

        // then
        coVerify { mockEventsDao.insertEvents(any()) }
    }

    @Test
    fun `searchEvents handles api exception and fallbacks to database`() {
        // given
        val eventsEntities = DataFixtures.getEventsEntityModels()
        val events = eventsEntities.map { it.toDomainModel() }

        coEvery { mockService.getEvents() } returns ApiResult.Exception(UnknownHostException())
        coEvery { mockEventsDao.getAllEvents() } returns eventsEntities

        // when
        val actual = runBlocking { cut.searchEvents() }

        // then
        actual shouldBeEqualTo Result.Success(events)
    }

    @Test
    fun `searchEvents handles api error `() {
        // given

        coEvery { mockService.getEvents() } returns mockk<ApiResult.Error<List<EventModel>>>()

        // when
        val actual = runBlocking { cut.searchEvents() }

        // then
        actual shouldBeEqualTo Result.Failure()
    }
}
