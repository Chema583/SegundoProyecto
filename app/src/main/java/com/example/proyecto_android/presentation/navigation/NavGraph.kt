package com.example.proyecto_android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.proyecto_android.presentation.detail.DetailScreen
import com.example.proyecto_android.presentation.detail.DetailViewModel
import com.example.proyecto_android.presentation.home.HomeScreen
import com.example.proyecto_android.presentation.home.HomeViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {
        composable(Routes.HOME) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(viewModel = homeViewModel, navController = navController)
        }

        composable("${Routes.DETAIL}/{countryName}") { backStackEntry ->
            val countryName = backStackEntry.arguments?.getString("countryName") ?: return@composable
            val detailViewModel: DetailViewModel = hiltViewModel()
            DetailScreen(countryName = countryName, viewModel = detailViewModel)
        }

        // Aquí puedes agregar la pantalla de login más adelante
        // composable(Routes.LOGIN) { ... }
    }
}
