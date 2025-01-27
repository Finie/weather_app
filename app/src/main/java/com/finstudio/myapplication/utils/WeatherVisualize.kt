package com.finstudio.myapplication.utils


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finstudio.myapplication.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import com.finstudio.myapplication.data.database.entity.LocationData
import com.finstudio.myapplication.data.database.entity.WeatherData
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun WeatherVisualize(weatherData: WeatherData?, forecastList: List<WeatherData>) {




    Column(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sea_rainy),
                contentDescription = "Image description",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop

            )

            // Column to stack the texts vertically
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "${weatherData?.fellsLike}°",
                    style = TextStyle(fontSize = 56.sp, color = Color.White, fontWeight = FontWeight.Bold)
                )


                Text(
                    text = "${weatherData?.weather}",
                    style = TextStyle(fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = "${weatherData?.condition}",
                    style = TextStyle(fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
                )
            }
        }

        ListItemComponentText("${weatherData?.minTemp}°", "${weatherData?.fellsLike}°", "${weatherData?.maxTemp}°")


        // Scrollable list
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items(forecastList.size) { forecast ->
                ListItemComponent(
                    leftTopText = forecastList[forecast].date,
                    icon = when (forecastList[forecast].weather) {
                        "Clear" -> Icons.Filled.WbSunny
                        "Rain" -> Icons.Filled.Umbrella
                        "Clouds" -> Icons.Filled.Cloud
                        else -> Icons.Filled.Umbrella
                    },
                    rightText = "${forecastList[forecast].fellsLike}°c",
                )
            }

        }


    }
}


@Composable
fun ListItemComponent(leftTopText: String, icon: ImageVector, rightText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), // Adjust padding as needed
        verticalAlignment = Alignment.CenterVertically, // Ensures all elements align vertically in the center
        horizontalArrangement = Arrangement.SpaceBetween // Ensures proper spacing between components
    ) {
        // Left Column (two texts stacked vertically)
        Column(
            modifier = Modifier
                .weight(1f) // Allows the left column to take up its fair share of space
                .padding(end = 8.dp), // Add some spacing between the left column and the icon
            verticalArrangement = Arrangement.Center // Ensures texts are vertically centered
        ) {
            Text(
                text = getDayOfWeek(leftTopText),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold) // Slightly larger and bold for emphasis
            )
            Text(
                text = getTime(leftTopText),
                style = TextStyle(fontSize = 14.sp, color = Color.Gray) // Smaller and gray for secondary text
            )
        }

        // Icon in the center (ensure it remains aligned)
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(24.dp), // Fixed size to keep it centered
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Icon"
            )
        }

        // Right Text
        Text(
            text = rightText,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.padding(start = 8.dp) // Add spacing if needed
        )
    }
}



//@Composable
//fun ListItemComponent(leftTopText: String, icon: ImageVector, rightText: String) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp), // Adjust padding as needed
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween // Ensures proper spacing between components
//    ) {
//        // Left Column (two texts stacked vertically)
//        Column(
//            modifier = Modifier
//                .padding(end = 8.dp) // Adjust padding as needed
//                .weight(1f), // Ensures proper space allocation
//            horizontalAlignment = Alignment.Start // Aligns text to the start (left)
//        ) {
//            Text(
//                text = getDayOfWeek(leftTopText),
//                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold) // Slightly larger and bold for emphasis
//            )
//            Text(
//                text = getTime(leftTopText),
//                style = TextStyle(fontSize = 14.sp, color = Color.Gray) // Smaller and gray for secondary text
//            )
//        }
//
//        // Icon in the center
//        Icon(
//            imageVector = icon,
//            contentDescription = "Icon",
//            modifier = Modifier
//                .padding(horizontal = 16.dp)
//                .size(24.dp) // Adjust size as needed
//        )
//
//        // Right Text
//        Text(
//            text = rightText,
//            style = TextStyle(fontSize = 18.sp),
//            modifier = Modifier.padding(start = 8.dp) // Adjust padding as needed
//        )
//    }
//}



@Composable
fun ListItemComponentText(
    leftTopText: String,
    centerTopText: String,
    rightTopText: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), // Adjust padding as needed
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Ensures spacing between left, center, and right
    ) {
        // Left Column (aligned center)
        Column(
            modifier = Modifier.weight(1f), // Ensures equal space distribution
            horizontalAlignment = Alignment.CenterHorizontally, // Align text to the center within the column
            verticalArrangement = Arrangement.Center // Center-align items vertically
        ) {
            Text(
                text = leftTopText,
                style = TextStyle(fontSize = 14.sp)
            )
            Text(
                text = "Min temp",
                style = TextStyle(fontSize = 12.sp, color = Color.Gray) // Slightly smaller and gray for distinction
            )
        }

        // Center Column (aligned center)
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally, // Align text to the center within the column
            verticalArrangement = Arrangement.Center // Center-align items vertically
        ) {
            Text(
                text = centerTopText,
                style = TextStyle(fontSize = 14.sp)
            )
            Text(
                text = "Current",
                style = TextStyle(fontSize = 12.sp, color = Color.Gray)
            )
        }

        // Right Column (aligned center)
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally, // Align text to the center within the column
            verticalArrangement = Arrangement.Center // Center-align items vertically
        ) {
            Text(
                text = rightTopText,
                style = TextStyle(fontSize = 14.sp)
            )
            Text(
                text = "Max temp",
                style = TextStyle(fontSize = 12.sp, color = Color.Gray)
            )
        }
    }
}





fun getDayOfWeek(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:00:00", Locale.getDefault())
    val outputFormat = SimpleDateFormat("EEEE, MMM dd", Locale.getDefault()) // Day of the week and date

    return try {
        val date = inputFormat.parse(inputDate)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        "Invalid date format"
    }
}


fun getTime(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:00:00", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:00", Locale.getDefault()) // Time in HH:00:00

    return try {
        val time = timeFormat.format(inputFormat.parse(inputDate))
        "$time"
    } catch (e: Exception) {
        "Invalid date format"
    }
}
