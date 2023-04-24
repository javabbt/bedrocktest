package com.test.bedrock.feature.home.data.repository

import com.test.bedrock.base.data.retrofit.ApiResult
import com.test.bedrock.base.domain.result.Result
import com.test.bedrock.feature.home.data.datasource.api.model.toDomainModel
import com.test.bedrock.feature.home.data.datasource.api.model.toEntityModel
import com.test.bedrock.feature.home.data.datasource.api.model.toEvent
import com.test.bedrock.feature.home.data.datasource.api.service.EventsRetrofitService
import com.test.bedrock.feature.home.data.datasource.database.EventsDao
import com.test.bedrock.feature.home.data.datasource.database.model.toDomainModel
import com.test.bedrock.feature.home.domain.model.Event
import com.test.bedrock.feature.home.domain.repository.EventRepository
import timber.log.Timber

internal class EventRepositoryImpl(
    private val eventsRetrofitService: EventsRetrofitService,
    private val eventsDao: EventsDao,
) : EventRepository {
    override suspend fun searchEvents(): Result<List<Event>> =
        when (val apiResult = eventsRetrofitService.getEvents()) {
            is ApiResult.Success -> {
                val events = apiResult
                    .data
                    .also { eventsApiModels ->
                        val eventsEntityModels = eventsApiModels.map { it.toEntityModel() }
                        eventsDao.insertEvents(eventsEntityModels)
                    }
                    .map { it.toDomainModel() }

                Result.Success(events)
            }
            is ApiResult.Error -> {
                Result.Failure()
            }
            is ApiResult.Exception -> {
                Timber.e(apiResult.throwable)

                val events = eventsDao
                    .getAllEvents()
                    .map { it.toDomainModel() }

                Result.Success(events)
            }
        }

    override suspend fun getEventInfo(name: String): Result<Event> =
        when (val apiResult = eventsRetrofitService.getEventDetail(name)) {
            is ApiResult.Success -> {
                Result.Success(apiResult.data.toEvent())
            }
            is ApiResult.Error -> {
                Result.Failure()
            }
            is ApiResult.Exception -> {
                Result.Failure()
            }
        }
}
