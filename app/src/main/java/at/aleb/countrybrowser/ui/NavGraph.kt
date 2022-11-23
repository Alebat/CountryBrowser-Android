package at.aleb.countrybrowser.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import at.aleb.countrybrowser.presentation.CountriesViewModel
import at.aleb.countrybrowser.presentation.CountryDetailsViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            val viewModel: CountriesViewModel = hiltViewModel()
            viewModel.update()
            CountriesScreen(
                countries = viewModel.countries.collectAsState(),
                onSelect = { navController.navigate("detail/${it}") },
                onRetry = viewModel::update
            )
        }
        composable("detail/{code}",
            arguments = listOf(navArgument("code") { type = NavType.StringType })) {
            val code = it.arguments?.getString("code")
            if (code == null) {
                navController.navigateUp()
                return@composable
            }
            val viewModel: CountryDetailsViewModel = hiltViewModel()
            val update = { viewModel.update(code); Unit }
            update()
            CountryDetailsScreen(
                viewModel.details.collectAsState(),
                navController::navigateUp,
                update
            )
        }
    }
}