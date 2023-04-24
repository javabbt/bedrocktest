package com.test.bedrock.feature.home.presentation.screen.eventdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import androidx.navigation.fragment.navArgs
import com.test.bedrock.base.common.res.Dimen
import com.test.bedrock.base.presentation.activity.BaseFragment
import com.test.bedrock.base.presentation.compose.composable.DataNotFoundAnim
import com.test.bedrock.base.presentation.compose.composable.PlaceholderImage
import com.test.bedrock.base.presentation.compose.composable.ProgressIndicator
import com.test.bedrock.feature.home.R
import com.test.bedrock.feature.home.presentation.screen.eventdetail.EventDetailViewModel.UiState
import com.test.bedrock.feature.home.presentation.screen.eventdetail.EventDetailViewModel.UiState.Content
import com.test.bedrock.feature.home.presentation.screen.eventdetail.EventDetailViewModel.UiState.Error
import com.test.bedrock.feature.home.presentation.screen.eventdetail.EventDetailViewModel.UiState.Loading
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.navigation.koinNavGraphViewModel

internal class EventDetailFragment : BaseFragment() {
    private val args: EventDetailFragmentArgs by navArgs()
    private val model: EventDetailViewModel by koinNavGraphViewModel(R.id.eventNavGraph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        model.onEnter(args)

        return ComposeView(requireContext()).apply {
            setContent {
                EventDetailScreen(uiStateFlow = model.uiStateFlow)
            }
        }
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
private fun EventDetailScreen(uiStateFlow: StateFlow<UiState>) {
    val uiState: UiState by uiStateFlow.collectAsStateWithLifecycle()

    uiState.let {
        when (it) {
            Error -> DataNotFoundAnim()
            Loading -> ProgressIndicator()
            is Content -> PhotoDetails(it)
        }
    }
}

@Composable
private fun PhotoDetails(content: Content) {
    Column(
        modifier = Modifier
            .padding(Dimen.screenContentPadding)
            .verticalScroll(rememberScrollState()),

    ) {
        ElevatedCard(
            modifier = Modifier
                .padding(Dimen.spaceS)
                .wrapContentSize(),
            colors = CardDefaults.cardColors(
                contentColor = Color.White,
            ),
        ) {
            PlaceholderImage(
                url = content.url,
                contentDescription = "event image",
                modifier = Modifier.fillMaxSize(),
            )
            Text(
                text = content.name,
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
            )
        }
    }
}
