package com.test.bedrock.feature.home

import com.test.bedrock.feature.home.data.dataModule
import com.test.bedrock.feature.home.domain.domainModule
import com.test.bedrock.feature.home.presentation.presentationModule

val featureHomeModules = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
