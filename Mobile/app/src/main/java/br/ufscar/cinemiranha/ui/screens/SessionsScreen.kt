package br.ufscar.cinemiranha.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.MovieResponse
import br.ufscar.cinemiranha.model.SessionResponse
import br.ufscar.cinemiranha.ui.composable.AppLogo
import br.ufscar.cinemiranha.ui.composable.ErrorView
import br.ufscar.cinemiranha.ui.composable.LoadingIndicator
import br.ufscar.cinemiranha.ui.composable.Stepper
import br.ufscar.cinemiranha.viewmodel.SessionsViewModel
import coil.compose.AsyncImage

private val SBg      = Color(0xFF1F2024)
private val SSurface = Color(0xFF2F3036)
private val SRed     = Color(0xFFBF0903)
private val SPrimary = Color(0xFFFAFAFA)
private val SSecond  = Color(0xFF8F9098)
private val SDivider = Color(0xFF494A50)

@Composable
fun SessionsScreen(movieId: Long, onBack: () -> Unit, onSessionSelected: (Long) -> Unit) {
    val vm: SessionsViewModel = viewModel(factory = SessionsViewModel.factory(movieId))
    val state by vm.uiState.collectAsState()

    Scaffold(
        topBar    = { SessionsTopBar(onBack = onBack) },
        bottomBar = { SessionsBottomBar() },
        containerColor = SBg
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
                    state.isLoading -> LoadingIndicator()
                    state.errorMessage != null -> ErrorView(
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
