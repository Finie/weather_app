package com.finstudio.myapplication.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finstudio.myapplication.ui.favourites.FavouriteScreen
import com.finstudio.myapplication.ui.favourites.LocationDetailScreen
import com.finstudio.myapplication.ui.map.MapScreen
import com.finstudio.myapplication.ui.weather_visual.WeatherComponent

@Composable
fun BottomNavigationController() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable("home") { WeatherComponent() }
            composable("map") { MapScreen() }
            composable("favourite") { FavouriteScreen(navController) }
            composable("locationDetail/{longitude}/{latitude}/{id}") { backStackEntry ->
                val longitude = backStackEntry.arguments?.getString("longitude")?.toDoubleOrNull()
                val latitude = backStackEntry.arguments?.getString("latitude")?.toDoubleOrNull()
                val locationId = backStackEntry.arguments?.getString("id")
                LocationDetailScreen(longitude = longitude, latitude = latitude, locationId = locationId)
            }
        }
    }
}