package br.ufscar.cinemiranha.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.os.LocaleListCompat
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.MovieResponse
import br.ufscar.cinemiranha.viewmodel.HomeViewModel
import coil.compose.AsyncImage

private val Background  = Color(0xFF1F2024)
private val Surface     = Color(0xFF2F3036)
private val AccentRed   = Color(0xFFBF0903)
private val TextPrimary = Color(0xFFFAFAFA)
private val TextSecond  = Color(0xFF8F9098)
private val AgeBadge    = Color(0xFFFF8C00)

@Composable
fun HomeScreen(
    onMovieClick: (Long) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar    = { TopBar() },
        bottomBar = { BottomBar() },
        containerColor = Background
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading -> LoadingState()
                state.errorMessage != null -> ErrorState(
                    message = state.errorMessage!!,
                    onRetry = { viewModel.loadMovies() }
                )
                else -> MovieContent(
                    nowPlaying    = state.nowPlayingMovies,
                    comingSoon    = state.comingSoonMovies,
                    onMovieClick  = onMovieClick
                )
            }
        }
    }
}

@Composable
private fun TopBar() {
    val currentLocales = AppCompatDelegate.getApplicationLocales()
    val isPtBr = !currentLocales.isEmpty && currentLocales[0]?.language == "pt"
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Surface)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier.size(28.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = stringResource(R.string.cd_menu),
                tint = TextPrimary,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        AppLogo(modifier = Modifier.height(40.dp))
        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(AccentRed)
                .clickable {
                    val tag = if (isPtBr) "en" else "pt-BR"
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(tag)
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isPtBr) "EN" else "PT",
                color = TextPrimary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun BottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Surface)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        AppLogo(modifier = Modifier.height(50.dp))
    }
}

@Composable
private fun AppLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = stringResource(R.string.cd_logo),
        modifier = modifier.wrapContentWidth(unbounded = true),
        contentScale = ContentScale.Fit,
        colorFilter = ColorFilter.tint(AccentRed)
    )
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = AccentRed)
    }
}

@Composable
private fun ErrorState(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            color = TextSecond,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = onRetry) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                tint = AccentRed,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(stringResource(R.string.retry), color = AccentRed)
        }
    }
}

@Composable
private fun MovieContent(
    nowPlaying: List<MovieResponse>,
    comingSoon: List<MovieResponse>,
    onMovieClick: (Long) -> Unit
) {
    LazyColumn(contentPadding = PaddingValues(vertical = 16.dp)) {
        item {
            MovieSection(
                title = stringResource(R.string.now_playing),
                movies       = nowPlaying,
                showDuration = true,
                onMovieClick = onMovieClick
            )
        }
        item { Spacer(modifier = Modifier.height(28.dp)) }
        item {
            MovieSection(
                title = stringResource(R.string.coming_soon),
                movies       = comingSoon,
                showDuration = false,
                onMovieClick = onMovieClick
            )
        }
    }
}

@Composable
private fun MovieSection(
    title: String,
    movies: List<MovieResponse>,
    showDuration: Boolean,
    onMovieClick: (Long) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, color = TextPrimary, fontSize = 17.sp, fontWeight = FontWeight.Bold)
            Text(text = stringResource(R.string.see_more), color = AccentRed, fontSize = 13.sp)
        }
        Spacer(modifier = Modifier.height(12.dp))
        if (movies.isEmpty()) {
            Text(
                text = stringResource(R.string.no_movies),
                color = TextSecond,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(movies) { movie ->
                    MovieCard(
                        movie        = movie,
                        showDuration = showDuration,
                        onClick      = { onMovieClick(movie.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieCard(movie: MovieResponse, showDuration: Boolean, onClick: () -> Unit) {
    Column(modifier = Modifier.width(128.dp).clickable { onClick() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(185.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Surface)
        ) {
            AsyncImage(
                model = movie.coverUrl,
                contentDescription = movie.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            movie.ageRatingLabel()?.let { label ->
                AgeBadgeBox(
                    label = label,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(5.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = movie.title,
            color = TextPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(2.dp))

        val subtitle = if (showDuration) movie.durationLabel() else movie.firstSessionDate()
        subtitle?.let {
            Text(text = it, color = TextSecond, fontSize = 11.sp)
        }
    }
}

@Composable
private fun AgeBadgeBox(label: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(3.dp))
            .background(AgeBadge)
            .padding(horizontal = 5.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, color = TextPrimary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
    }
}
