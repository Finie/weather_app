package com.finstudio.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.finstudio.myapplication.ui.map.LocationViewModel
import com.finstudio.myapplication.ui.map.MapScreen
import com.finstudio.myapplication.ui.navigation.BottomNavigationController
import com.finstudio.myapplication.ui.theme.WeatherAppTheme
import com.finstudio.myapplication.ui.weather_visual.WeatherComponent
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    private val viewModel: LocationViewModel by viewModels()

    // Permission launcher for requesting location permissions
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            viewModel.fetchLocation()
        } else {
            Toast.makeText(this, "Location permissions are required.", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Places.initialize(applicationContext, "AIzaSyCMP2V0Wr1Vfq4mYxraRTrRRBhp15DOh4w")

        enableEdgeToEdge()
        // Request location permissions when the activity is created
        requestLocationPermissions()

        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BottomNavigationController()
                }

            }
        }
    }

    private fun requestLocationPermissions() {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

}