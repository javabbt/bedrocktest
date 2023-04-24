package com.test.bedrock.feature.home.presentation.screen.events

import com.test.bedrock.base.domain.result.Result
import com.test.bedrock.base.presentation.nav.NavManager
import com.test.bedrock.feature.home.domain.model.Event
import com.test.bedrock.feature.home.domain.usecase.GetEventsListUseCase
import com.test.bedrock.feature.home.presentation.screen.events.EventsListViewModel.UiState
import com.test.bedrock.library.testutils.CoroutinesTestDispatcherExtension
import com.test.bedrock.library.testutils.InstantTaskExecutorExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantTaskExecutorExtension::class, CoroutinesTestDispatcherExtension::class)
class EventsListViewModelTest {

    private val mockGetEventsListUseCase: GetEventsListUseCase = GetEventsListUseCase(
        mockk(),
    )

    private val mockNavManager: NavManager = mockk(relaxed = true)

    private val cut = EventsListViewModel(
        mockNavManager,
        mockGetEventsListUseCase,
    )

    @Test
    fun `onEnter emits state error`() = runTest {
        // givenx
        coEvery { mockGetEventsListUseCase() } returns Result.Success(emptyList())

        // when
        cut.onEnter()

        // then
        advanceUntilIdle()

        cut.uiStateFlow.value shouldBeEqualTo UiState.Error
    }

    @Test
    fun `onEnter emits state success`() = runTest {
        // given
        val event = Event("image", "test")
        val events = listOf(event)
        coEvery { mockGetEventsListUseCase() } returns Result.Success(events)

        // when
        cut.onEnter()

        // then
        advanceUntilIdle()

        cut.uiStateFlow.value shouldBeEqualTo UiState.Content(
            events = events,
        )
    }

    @Test
    fun `onEventClick navigate to event detail`() {
        val event = Event(
            name = "name",
            imageUrl = "test",
        )

        val navDirections = EventsFragmentDirections.actionEventListToEventDetail(
            "name",
        )

        // when
        cut.onEventClick(event)

        // then
        coVerify { mockNavManager.navigate(navDirections) }
    }
}
