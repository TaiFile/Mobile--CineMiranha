package br.ufscar.cinemiranha.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.ufscar.cinemiranha.model.dto.MovieResponse
import br.ufscar.cinemiranha.model.dto.SessionResponse
import br.ufscar.cinemiranha.ui.composable.Session.SessionsContent
import br.ufscar.cinemiranha.ui.composable._shared.Stepper
import br.ufscar.cinemiranha.ui.composable._shared.BottomBar
import br.ufscar.cinemiranha.ui.composable._shared.ErrorState
import br.ufscar.cinemiranha.ui.composable._shared.LoadingState
import br.ufscar.cinemiranha.ui.composable._shared.TopBar

@Composable
fun SessionsScreen(
    isLoading: Boolean,
    errorMessage: String?,
    movie: MovieResponse?,
    sessions: List<SessionResponse>,
    selectedDate: String?,
    selectedSubtitle: String?,
    onSessionSelected: (Long) -> Unit,
    onDateSelected: (String?) -> Unit,
    onSubtitleSelected: (String?) -> Unit,
    onBack: () -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar    = { TopBar(onBack = onBack) },
        bottomBar = { BottomBar() },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Stepper(currentStep = 0)

            Box(modifier = Modifier.weight(1f)) {
                when {
                    isLoading            -> LoadingState()
                    errorMessage != null -> ErrorState(message = errorMessage, onRetry = onRetry)
                    else -> SessionsContent(
                        movie              = movie,
                        sessions           = sessions,
                        selectedDate       = selectedDate,
                        selectedSubtitle   = selectedSubtitle,
                        onDateSelected     = onDateSelected,
                        onSubtitleSelected = onSubtitleSelected,
                        onSessionSelected  = onSessionSelected
                    )
                }
            }
        }
    }
}
