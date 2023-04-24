package com.test.bedrock.feature.home.domain

import com.test.bedrock.feature.home.domain.usecase.GetEventInfoUseCase
import com.test.bedrock.feature.home.domain.usecase.GetEventsListUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val domainModule = module {

    singleOf(::GetEventsListUseCase)

    singleOf(::GetEventInfoUseCase)
}
