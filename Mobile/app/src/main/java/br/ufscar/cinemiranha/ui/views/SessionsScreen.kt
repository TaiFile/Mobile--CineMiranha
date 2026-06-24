package br.ufscar.cinemiranha.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import br.ufscar.cinemiranha.ui.composable.Session.SessionsContent
import br.ufscar.cinemiranha.ui.composable.Stepper
import br.ufscar.cinemiranha.ui.composable._shared.BottomBar
import br.ufscar.cinemiranha.ui.composable._shared.ErrorState
import br.ufscar.cinemiranha.ui.composable._shared.LoadingState
import br.ufscar.cinemiranha.ui.composable._shared.TopBar
import br.ufscar.cinemiranha.viewmodel.SessionsViewModel

@Composable
fun SessionsScreen(movieId: Long, onBack: () -> Unit, onSessionSelected: (Long) -> Unit) {
    val vm: SessionsViewModel = viewModel(factory = SessionsViewModel.factory(movieId))
    val state = vm.uiState

    Scaffold(
        topBar    = { TopBar() },
        bottomBar = { BottomBar() },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Stepper(currentStep = 0)
            
            Box(
                modifier = Modifier.weight(1f)
            ) {
                when {
                    state.isLoading -> LoadingState()
                    state.errorMessage != null -> ErrorState(
                        message = state.errorMessage!!,
                        onRetry = { vm.load() }
                    )
                    else -> SessionsContent(
                        movie           = state.movie,
                        sessions        = state.sessions,
                        selectedDate    = state.selectedDate,
                        selectedSubtitle = state.selectedSubtitle,
                        selectedFormat  = state.selectedFormat,
                        onDateSelected  = { vm.selectDate(it) },
                        onSubtitleSelected = { vm.selectSubtitle(it) },
                        onFormatSelected   = { vm.selectFormat(it) },
                        onSessionSelected = onSessionSelected
                    )
                }
            }
        }
    }
}
