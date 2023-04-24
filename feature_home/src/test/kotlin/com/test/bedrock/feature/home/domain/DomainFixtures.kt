package com.test.bedrock.feature.home.domain

import com.test.bedrock.feature.home.domain.model.Event

object DomainFixtures {

    internal fun getEvent(): Event = Event("url", "test")
}
