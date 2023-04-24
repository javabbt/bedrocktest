package com.test.bedrock.feature.home.presentation.screen.events

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.test.bedrock.base.domain.result.Result
import com.test.bedrock.base.presentation.nav.NavManager
import com.test.bedrock.base.presentation.viewmodel.BaseAction
import com.test.bedrock.base.presentation.viewmodel.BaseState
import com.test.bedrock.base.presentation.viewmodel.BaseViewModel
import com.test.bedrock.feature.home.domain.model.Event
import com.test.bedrock.feature.home.domain.usecase.GetEventsListUseCase
import com.test.bedrock.feature.home.presentation.screen.events.EventsListViewModel.Action
import com.test.bedrock.feature.home.presentation.screen.events.EventsListViewModel.UiState
import com.test.bedrock.feature.home.presentation.screen.events.EventsListViewModel.UiState.Content
import com.test.bedrock.feature.home.presentation.screen.events.EventsListViewModel.UiState.Error
import com.test.bedrock.feature.home.presentation.screen.events.EventsListViewModel.UiState.Loading
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class EventsListViewModel(
    private val navManager: NavManager,
    private val getEventsListUseCase: GetEventsListUseCase,
) : BaseViewModel<UiState, Action>(Loading) {

    fun onEnter() {
        getEventList()
    }

    private var job: Job? = null

    private fun getEventList() {
        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            getEventsListUseCase().also { result ->
                val action = when (result) {
                    is Result.Success -> {
                        if (result.value.isEmpty()) {
                            Action.EventsListLoadFailure
                        } else {
                            Action.EventsListLoadSuccess(result.value)
                        }
                    }
                    is Result.Failure -> {
                        Action.EventsListLoadFailure
                    }
                }
                sendAction(action)
            }
        }
    }

    fun onEventClick(event: Event) {
        val navDirections =
            EventsFragmentDirections.actionEventListToEventDetail(event.name)

        navManager.navigate(navDirections)
    }

    internal sealed interface Action : BaseAction<UiState> {
        class EventsListLoadSuccess(private val events: List<Event>) : Action {
            override fun reduce(state: UiState) = Content(events)
        }

        object EventsListLoadFailure : Action {
            override fun reduce(state: UiState) = Error
        }
    }

    @Immutable
    internal sealed interface UiState : BaseState {
        data class Content(val events: List<Event>) : UiState
        object Loading : UiState
        object Error : UiState
    }
}
