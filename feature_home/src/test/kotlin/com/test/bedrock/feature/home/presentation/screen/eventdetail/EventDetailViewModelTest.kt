package com.test.bedrock.feature.home.presentation.screen.eventdetail

import com.test.bedrock.base.domain.result.Result
import com.test.bedrock.feature.home.domain.usecase.GetEventInfoUseCase
import com.test.bedrock.feature.home.presentation.screen.eventdetail.EventDetailViewModel.UiState
import com.test.bedrock.library.testutils.CoroutinesTestDispatcherExtension
import com.test.bedrock.library.testutils.InstantTaskExecutorExtension
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantTaskExecutorExtension::class, CoroutinesTestDispatcherExtension::class)
class EventDetailViewModelTest {

    private val mockGetEventInfoUseCase: GetEventInfoUseCase = mockk()

    private val cut = EventDetailViewModel(
        mockGetEventInfoUseCase,
    )

    @Test
    @Disabled("mockk cannot mock this function")
    fun `onEnter event is not found`() = runTest {
        // given
        val id = 1.toString()

        val mockEventsDetailFragmentArgs = EventDetailFragmentArgs(id)

        coEvery {
            mockGetEventInfoUseCase(id)
        } returns Result.Failure()

        // when
        cut.onEnter(mockEventsDetailFragmentArgs)

        // then
        cut.uiStateFlow.value shouldBeEqualTo UiState.Error
    }
}
