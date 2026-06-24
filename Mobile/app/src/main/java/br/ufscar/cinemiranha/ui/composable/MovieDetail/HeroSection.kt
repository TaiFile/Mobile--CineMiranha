package br.ufscar.cinemiranha.ui.composable.MovieDetail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.MovieResponse
import coil.compose.AsyncImage

@Composable
fun HeroSection(movie: MovieResponse) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        AsyncImage(
            model = movie.coverUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background),
                        startY = 100f
                    )
                )
        )

        if (!movie.trailerUrl.isNullOrBlank()) {
            val context = LocalContext.current
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.85f))
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.trailerUrl))
                        context.startActivity(intent)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = stringResource(R.string.cd_trailer),
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = 55.dp)
        ) {
            AsyncImage(
                model = movie.coverUrl,
                contentDescription = movie.title,
                modifier = Modifier
                    .width(100.dp)
                    .height(145.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.surface),
                contentScale = ContentScale.Crop
            )
        }
    }

    Spacer(modifier = Modifier.height(60.dp))
}