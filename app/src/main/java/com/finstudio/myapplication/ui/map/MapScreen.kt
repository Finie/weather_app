package com.finstudio.myapplication.ui.map


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.finstudio.myapplication.data.database.entity.LocationData
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import java.util.UUID


@Composable
fun MapScreen(locationViewModel: LocationViewModel = hiltViewModel()) {

    var query by remember { mutableStateOf(TextFieldValue("")) }
    var predictions by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }
    var selectedPlace by remember { mutableStateOf<Place?>(null) }
    var locationName: String? by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }



    val userLocation = locationViewModel.location.collectAsState()


    var latitude = userLocation.value?.latitude ?: 0.0
    var longitude = userLocation.value?.longitude ?: 0.0


    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(latitude, longitude), 10f)
    }


    val placesClient: PlacesClient = Places.createClient(LocalContext.current)

    // Keyboard controller to dismiss the keyboard
    val keyboardController = LocalSoftwareKeyboardController.current

    // Search logic
    LaunchedEffect(query.text) {
        if (query.text.isNotEmpty()) {
            val request = FindAutocompletePredictionsRequest.builder().setQuery(query.text).build()

            placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
                    predictions = response.autocompletePredictions
                }.addOnFailureListener {
                    predictions = emptyList()
                }
        } else {
            predictions = emptyList()
        }


    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Map
        GoogleMap(
            modifier = Modifier.fillMaxSize(), cameraPositionState = cameraPositionState
        ) {
            userLocation.let { location ->
                val latLng =
                    LatLng(location.value?.latitude ?: 0.0, location.value?.longitude ?: 0.0)
                Marker(
                    state = MarkerState(position = latLng),
                    title = "Your Location",
                    snippet = "This is where you are currently located."
                )
            }

            // Show marker for the selected place
            selectedPlace?.latLng?.let {
                Marker(
                    state = MarkerState(position = it),
                    title = selectedPlace?.name,
                    snippet = selectedPlace?.address
                )
            }
        }

        // Search Bar and Predictions
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding() // Ensures the search bar is below the status bar
                .padding(16.dp) // Add additional padding for better spacing
        ) {
            TextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
            )

            // Prediction List

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)

            ) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    itemsIndexed(predictions) { index, prediction ->
                        Text(
                            text = prediction.getPrimaryText(null).toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    // Fetch place details for the selected prediction
                                    val placeId = prediction.placeId
                                    val placeFields = listOf(Place.Field.ID, Place.Field.LAT_LNG)
                                    val placeRequest =
                                        FetchPlaceRequest.builder(placeId, placeFields).build()

                                    placesClient.fetchPlace(placeRequest)
                                        .addOnSuccessListener { response ->
                                            selectedPlace = response.place
                                            // Move camera to the selected place
                                            selectedPlace?.latLng?.let {
                                                cameraPositionState.position =
                                                    CameraPosition.fromLatLngZoom(it, 10f)
                                            }
                                            showDialog = true
                                            locationName = query.text
                                            query = TextFieldValue("")
                                            keyboardController?.hide()
                                        }.addOnFailureListener {
                                            // Handle failure
                                        }
                                }
                                .padding(8.dp))
                    }
                }

            }
        }

        ShowAlertDialog(
            showDialog = showDialog,
            onDismiss = { action ->
                showDialog = false // Close the dialog
                when (action) {
                    DialogAction.SAVE -> {
                        // Handle Save action

                        locationViewModel.saveLocationData(LocationData(
                            id = UUID.randomUUID(),
                            longitude = selectedPlace?.latLng?.longitude ?: 0.0,
                            latitude = selectedPlace?.latLng?.latitude ?: 0.0,
                            name = locationName.toString(),
                            isFavourite = true,
                            active = false
                        ))


                    }
                    DialogAction.CANCEL -> {
                        // Handle Cancel action
                        println("User clicked Cancel")
                    }
                }
            }
        )
    }
}


@Composable
fun ShowAlertDialog(
    showDialog: Boolean, onDismiss: (DialogAction) -> Unit
) {
    // Show the AlertDialog when showDialog is true
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss(DialogAction.CANCEL) }, // Close the dialog and return Cancel
            title = { Text(text = "Confirm Action") },
            text = { Text("Do you like to add this location to favourites?") },
            confirmButton = {
                Button(
                    onClick = {
                        // Handle Save action
                        onDismiss(DialogAction.SAVE)
                    }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        // Handle Cancel action
                        onDismiss(DialogAction.CANCEL)
                    }) {
                    Text("Cancel")
                }
            })
    }
}

enum class DialogAction {
    SAVE, CANCEL
}