package com.finstudio.myapplication.ui.favourites


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finstudio.myapplication.data.database.entity.LocationData


@Composable
fun FavouriteScreen(navController: NavController, favouriteViewModel: FavouriteViewModel = hiltViewModel()) {
    val favouriteLocationList by favouriteViewModel.locationList.collectAsState(emptyList())

    Column {
        Text(text = "Favourite Screen", modifier = Modifier.padding(16.dp))

        LazyColumn {
            items(favouriteLocationList.size) { location ->
                LocationItem(favouriteLocationList[location], navController)
            }
        }
    }
}


@Composable
fun LocationItem(location: LocationData, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                // Navigate to the LocationDetailScreen with longitude and latitude
                navController.navigate(
                    "locationDetail/${location.longitude}/${location.latitude}/${location.id}"
                )
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = location.name)
        Text(text = "(${location.latitude}, ${location.longitude})")
    }
}
