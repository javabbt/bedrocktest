package com.test.bedrock.feature.home.data.datasource.api.model

import com.test.bedrock.feature.home.data.DataFixtures
import com.test.bedrock.feature.home.domain.model.Event
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

class EventApiModelTest {

    @Test
    fun `data model with full data maps to EventDomainModel`() {
        // given
        val cut = DataFixtures.getEventApiModel("1")

        // when
        val domainModel = cut.toDomainModel()

        // then
        domainModel shouldBeEqualTo Event(
            name = cut.name,
            imageUrl = cut.imageUrl,
        )
    }
}
