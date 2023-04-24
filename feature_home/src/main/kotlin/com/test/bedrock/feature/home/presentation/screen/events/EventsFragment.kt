package com.test.bedrock.feature.home.presentation.screen.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.bedrock.base.common.res.Dimen
import com.test.bedrock.base.presentation.activity.BaseFragment
import com.test.bedrock.base.presentation.compose.composable.DataNotFoundAnim
import com.test.bedrock.base.presentation.compose.composable.PlaceholderImage
import com.test.bedrock.base.presentation.compose.composable.ProgressIndicator
import com.test.bedrock.feature.home.R
import com.test.bedrock.feature.home.domain.model.Event
import com.test.bedrock.feature.home.presentation.screen.events.EventsListViewModel.UiState
import com.test.bedrock.feature.home.presentation.screen.events.EventsListViewModel.UiState.Content
import com.test.bedrock.feature.home.presentation.screen.events.EventsListViewModel.UiState.Error
import com.test.bedrock.feature.home.presentation.screen.events.EventsListViewModel.UiState.Loading
import org.koin.androidx.navigation.koinNavGraphViewModel

class EventsFragment : BaseFragment() {

    private val model: EventsListViewModel by koinNavGraphViewModel(R.id.eventNavGraph)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                EventListScreen(model)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.onEnter()
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
private fun EventListScreen(viewModel: EventsListViewModel) {
    val uiState: UiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    uiState.let {
        when (it) {
            Error -> DataNotFoundAnim()
            Loading -> ProgressIndicator()
            is Content -> PhotoGrid(events = it.events, viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PhotoGrid(events: List<Event>, viewModel: EventsListViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(Dimen.imageSize),
        contentPadding = PaddingValues(Dimen.screenContentPadding),
    ) {
        items(events.size) { index ->
            val event = events[index]
            ElevatedCard(
                modifier = Modifier
                    .padding(Dimen.spaceS)
                    .wrapContentSize(),
                onClick = { viewModel.onEventClick(event) },
                colors = CardDefaults.cardColors(
                    contentColor = Color.White,
                ),
            ) {
                PlaceholderImage(
                    url = event.imageUrl,
                    contentDescription = "event image",
                    modifier = Modifier.width(100.dp).height(100.dp),
                )
                Text(
                    text = event.name,
                    modifier = Modifier
                        .padding(15.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
