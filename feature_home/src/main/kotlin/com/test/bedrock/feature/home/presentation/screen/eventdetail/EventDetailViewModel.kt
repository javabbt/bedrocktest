package com.test.bedrock.feature.home.presentation.screen.eventdetail

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.test.bedrock.base.domain.result.Result
import com.test.bedrock.base.presentation.viewmodel.BaseAction
import com.test.bedrock.base.presentation.viewmodel.BaseState
import com.test.bedrock.base.presentation.viewmodel.BaseViewModel
import com.test.bedrock.feature.home.domain.model.Event
import com.test.bedrock.feature.home.domain.usecase.GetEventInfoUseCase
import com.test.bedrock.feature.home.presentation.screen.eventdetail.EventDetailViewModel.Action
import com.test.bedrock.feature.home.presentation.screen.eventdetail.EventDetailViewModel.Action.EventDetailLoadSuccess
import com.test.bedrock.feature.home.presentation.screen.eventdetail.EventDetailViewModel.Action.EventLoadFailure
import com.test.bedrock.feature.home.presentation.screen.eventdetail.EventDetailViewModel.UiState
import com.test.bedrock.feature.home.presentation.screen.eventdetail.EventDetailViewModel.UiState.Content
import com.test.bedrock.feature.home.presentation.screen.eventdetail.EventDetailViewModel.UiState.Loading
import kotlinx.coroutines.launch

internal class EventDetailViewModel(
    private val getEventInfoUseCase: GetEventInfoUseCase,
) : BaseViewModel<UiState, Action>(Loading) {

    fun onEnter(args: EventDetailFragmentArgs) {
        getEvent(args)
    }

    private fun getEvent(args: EventDetailFragmentArgs) {
        viewModelScope.launch {
            getEventInfoUseCase(args.name).also {
                when (it) {
                    is Result.Success -> {
                        sendAction(EventDetailLoadSuccess(it.value))
                    }
                    is Result.Failure -> sendAction(EventLoadFailure)
                }
            }
        }
    }

    internal sealed interface Action : BaseAction<UiState> {
        class EventDetailLoadSuccess(private val eventDetail: Event) : Action {
            override fun reduce(state: UiState) = Content(
                name = eventDetail.name,
                url = eventDetail.imageUrl,
            )
        }

        object EventLoadFailure : Action {
            override fun reduce(state: UiState) = UiState.Error
        }
    }

    @Immutable
    internal sealed interface UiState : BaseState {
        data class Content(
            val name: String,
            val url: String,
        ) : UiState

        object Loading : UiState
        object Error : UiState
    }
}
