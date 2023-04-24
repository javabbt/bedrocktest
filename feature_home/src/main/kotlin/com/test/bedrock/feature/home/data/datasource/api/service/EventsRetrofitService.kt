package com.test.bedrock.feature.home.data.datasource.api.service

import com.test.bedrock.base.data.retrofit.ApiResult
import com.test.bedrock.feature.home.data.datasource.api.model.EventDetailResponse
import com.test.bedrock.feature.home.data.datasource.api.model.EventModel
import retrofit2.http.GET
import retrofit2.http.Query

internal interface EventsRetrofitService {
    @GET("test/json.php")
    suspend fun getEvents(): ApiResult<List<EventModel>>

    @GET("test/json.php")
    suspend fun getEventDetail(
        @Query("name") name: String,
    ): ApiResult<EventDetailResponse>
}
