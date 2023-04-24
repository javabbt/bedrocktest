package com.test.bedrock.feature.home.presentation

import coil.ImageLoader
import com.test.bedrock.feature.home.presentation.screen.eventdetail.EventDetailViewModel
import com.test.bedrock.feature.home.presentation.screen.events.EventsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val presentationModule = module {
    viewModelOf(::EventsListViewModel)

    single { ImageLoader(get()) }

    viewModelOf(::EventDetailViewModel)
}
