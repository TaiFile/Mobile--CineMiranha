package br.ufscar.cinemiranha

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.ufscar.cinemiranha.model.dto.CheckoutRequest
import br.ufscar.cinemiranha.repository.SnackRepository
import br.ufscar.cinemiranha.viewmodel.HomeViewModel
import br.ufscar.cinemiranha.viewmodel.MovieDetailViewModel
import br.ufscar.cinemiranha.viewmodel.OrderSummaryViewModel
import br.ufscar.cinemiranha.viewmodel.PurchaseHistoryViewModel
import br.ufscar.cinemiranha.viewmodel.SessionsViewModel
import br.ufscar.cinemiranha.ui.views.HomeScreen
import br.ufscar.cinemiranha.ui.views.MovieDetailScreen
import br.ufscar.cinemiranha.ui.views.OrderSummaryScreen
import br.ufscar.cinemiranha.ui.views.PurchaseHistoryScreen
import br.ufscar.cinemiranha.ui.views.SeatsScreen
import br.ufscar.cinemiranha.ui.views.SessionsScreen
import br.ufscar.cinemiranha.ui.views.SnacksScreen
import br.ufscar.cinemiranha.ui.views.SuccessScreen
import br.ufscar.cinemiranha.ui.views.TicketsScreen
import br.ufscar.cinemiranha.viewmodel.SeatsViewModel
import br.ufscar.cinemiranha.viewmodel.SnacksViewModel
import br.ufscar.cinemiranha.viewmodel.TicketsViewModel

@Composable
fun MainAppNavigation(navController: NavHostController) {
    val context = LocalContext.current.applicationContext

    val seatsViewModel: SeatsViewModel = viewModel()
    val ticketsViewModel: TicketsViewModel = viewModel()
    val snacksViewModel: SnacksViewModel = viewModel(
        factory = SnacksViewModel.factory(SnackRepository())
    )
    val orderSummaryViewModel: OrderSummaryViewModel = viewModel(
        factory = OrderSummaryViewModel.factory(context)
    )

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
            HomeScreen(
                isLoading    = homeViewModel.uiState.isLoading,
                errorMessage = homeViewModel.uiState.errorMessage,
                nowPlaying   = homeViewModel.uiState.nowPlayingMovies,
                comingSoon   = homeViewModel.uiState.comingSoonMovies,
                onMovieClick = { movieId ->
                    navController.navigate("movie_detail/$movieId")
                },
                onViewHistory = { navController.navigate("history") },
                onRetry = { homeViewModel.loadMovies() }
            )
        }

        composable(
            route = "movie_detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.LongType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            val vm: MovieDetailViewModel = viewModel(factory = MovieDetailViewModel.factory(movieId))
            MovieDetailScreen(
                isLoading    = vm.uiState.isLoading,
                errorMessage = vm.uiState.errorMessage,
                movie        = vm.uiState.movie,
                onBuyTickets = { navController.navigate("sessions/$movieId") },
                onBack       = { navController.popBackStack() },
                onRetry      = { vm.loadMovie() }
            )
        }

        composable(
            route = "sessions/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.LongType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            val vm: SessionsViewModel = viewModel(factory = SessionsViewModel.factory(movieId))
            SessionsScreen(
                isLoading          = vm.uiState.isLoading,
                errorMessage       = vm.uiState.errorMessage,
                movie              = vm.uiState.movie,
                sessions           = vm.uiState.sessions,
                selectedDate       = vm.uiState.selectedDate,
                selectedSubtitle   = vm.uiState.selectedSubtitle,
                onSessionSelected  = { sessionId ->
                    seatsViewModel.reset()
                    ticketsViewModel.reset()
                    snacksViewModel.reset()
                    navController.navigate("seats/$movieId/$sessionId")
                },
                onDateSelected     = { vm.selectDate(it) },
                onSubtitleSelected = { vm.selectSubtitle(it) },
                onBack             = { navController.popBackStack() },
                onRetry            = { vm.load() }
            )
        }

        composable(
            route = "seats/{movieId}/{sessionId}",
            arguments = listOf(
                navArgument("movieId")   { type = NavType.LongType },
                navArgument("sessionId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val movieId   = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            val sessionsVm: SessionsViewModel = viewModel(factory = SessionsViewModel.factory(movieId))
            val sessionsState = sessionsVm.uiState
            val session = sessionsState.sessions.find { it.id == sessionId }
            val seatsState = seatsViewModel.uiState

            SeatsScreen(
                movie         = sessionsState.movie,
                session       = session,
                selectedSeats = seatsState.selectedSeats,
                onSeatToggle  = { seat -> seatsViewModel.toggleSeat(seat) },
                onBack        = { navController.popBackStack() },
                onNext        = { navController.navigate("tickets/$movieId/$sessionId") }
            )
        }

        composable(
            route = "tickets/{movieId}/{sessionId}",
            arguments = listOf(
                navArgument("movieId")   { type = NavType.LongType },
                navArgument("sessionId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val movieId   = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            val sessionsVm: SessionsViewModel = viewModel(factory = SessionsViewModel.factory(movieId))
            val sessionsState = sessionsVm.uiState
            val session = sessionsState.sessions.find { it.id == sessionId }
            val seatsState = seatsViewModel.uiState
            val ticketsState = ticketsViewModel.uiState
            val fullPrice = (session?.priceInCents ?: 0) / 100f
            val halfPrice = fullPrice / 2f

            TicketsScreen(
                movie              = sessionsState.movie,
                session            = session,
                selectedSeatsCount = seatsState.selectedSeats.size,
                selectedSeatsLabel = seatsState.selectedSeats.joinToString(", "),
                fullPriceCount     = ticketsState.fullPriceCount,
                halfPriceCount     = ticketsState.halfPriceCount,
                fullPrice          = fullPrice,
                halfPrice          = halfPrice,
                onTicketCountsChanged = { full, half ->
                    ticketsViewModel.setTicketCounts(full, half)
                },
                onBack             = { navController.popBackStack() },
                onNext             = { navController.navigate("snacks/$movieId/$sessionId") }
            )
        }

        composable(
            route = "snacks/{movieId}/{sessionId}",
            arguments = listOf(
                navArgument("movieId")   { type = NavType.LongType },
                navArgument("sessionId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val movieId   = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            val snacksState = snacksViewModel.uiState

            SnacksScreen(
                isLoading       = snacksState.isLoading,
                errorMessage    = snacksState.errorMessage,
                availableSnacks = snacksState.availableSnacks,
                selectedSnacks  = snacksState.selectedSnacks,
                onUpdateSnackQuantity = { id, delta ->
                    snacksViewModel.updateSnackQuantity(id, delta)
                },
                onRetry         = { snacksViewModel.loadSnacks() },
                onBack          = { navController.popBackStack() },
                onNext          = { navController.navigate("summary/$movieId/$sessionId") }
            )
        }

        composable(
            route = "summary/{movieId}/{sessionId}",
            arguments = listOf(
                navArgument("movieId")   { type = NavType.LongType },
                navArgument("sessionId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val movieId   = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            val sessionsVm: SessionsViewModel = viewModel(factory = SessionsViewModel.factory(movieId))
            val sessionsState = sessionsVm.uiState
            val session = sessionsState.sessions.find { it.id == sessionId }
            val seatsState = seatsViewModel.uiState
            val ticketsState = ticketsViewModel.uiState
            val snacksState = snacksViewModel.uiState
            val fullPrice = (session?.priceInCents ?: 0) / 100f
            val halfPrice = fullPrice / 2f
            val orderState = orderSummaryViewModel.uiState

            OrderSummaryScreen(
                movie           = sessionsState.movie,
                session         = session,
                selectedSeats   = seatsState.selectedSeats,
                fullPriceCount  = ticketsState.fullPriceCount,
                halfPriceCount  = ticketsState.halfPriceCount,
                ticketTotal     = ticketsViewModel.getTotalPrice(fullPrice, halfPrice),
                snackTotal      = snacksViewModel.getTotalSnackPrice(),
                selectedSnacks  = snacksState.selectedSnacks,
                availableSnacks = snacksState.availableSnacks,
                isConfirming    = orderState.isConfirming,
                confirmError    = orderState.confirmError,
                onBack          = { navController.popBackStack() },
                onNext          = {
                    val request = CheckoutRequest(
                        sessionId   = sessionId,
                        seats       = seatsState.selectedSeats,
                        fullTickets = ticketsState.fullPriceCount,
                        halfTickets = ticketsState.halfPriceCount,
                        totalPrice  = ticketsViewModel.getTotalPrice(fullPrice, halfPrice) + snacksViewModel.getTotalSnackPrice()
                    )
                    orderSummaryViewModel.confirmPurchase(
                        request      = request,
                        movieTitle   = sessionsState.movie?.title ?: "",
                        sessionDate  = session?.dateDayLabel() ?: "",
                        sessionTime  = session?.timeLabel() ?: ""
                    ) {
                        navController.navigate("success") {
                            popUpTo("home") { inclusive = false }
                        }
                    }
                }
            )
        }

        composable("success") {
            SuccessScreen(
                onBackToMenu = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onViewHistory = {
                    navController.navigate("history")
                }
            )
        }

        composable("history") {
            val vm: PurchaseHistoryViewModel = viewModel(
                factory = PurchaseHistoryViewModel.factory(context)
            )
            PurchaseHistoryScreen(
                purchases = vm.uiState.purchases,
                isLoading = vm.uiState.isLoading,
                onBack    = { navController.popBackStack() }
            )
        }
    }
}
