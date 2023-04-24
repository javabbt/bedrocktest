package com.test.bedrock.feature.home.data

import androidx.room.Room
import com.test.bedrock.feature.home.data.datasource.api.service.EventsRetrofitService
import com.test.bedrock.feature.home.data.datasource.database.EventDatabase
import com.test.bedrock.feature.home.data.repository.EventRepositoryImpl
import com.test.bedrock.feature.home.domain.repository.EventRepository
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dataModule = module {

    single<EventRepository> { EventRepositoryImpl(get(), get()) }

    single { get<Retrofit>().create(EventsRetrofitService::class.java) }

    single {
        Room.databaseBuilder(
            get(),
            EventDatabase::class.java,
            "Events.db",
        ).build()
    }

    single { get<EventDatabase>().events() }
}
