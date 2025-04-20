package com.example.weatherapp.presentation


import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun WeatherHomeScreen() {
    val backgroundColor = Color(0xFF0D0D0D)
    val cardColor = Color(0xFF1C1C1E)
    val textColor = Color.White
    val secondaryTextColor = Color(0xFFB0B0B0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(20.dp)
    ) {
        Text(
            text = "Welcome home,\nSaad Shaikh",
            color = secondaryTextColor,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Main Weather Card
        Card(
            backgroundColor = cardColor,
            shape = RoundedCornerShape(24.dp),
            elevation = 6.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("04 August 2024", color = secondaryTextColor, fontSize = 13.sp)
                Text("Cloudy", color = secondaryTextColor, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "18°C",
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.star_on), // Use your cloudy icon here
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    WeatherStatDark("10 m/s", "Wind")
                    WeatherStatDark("98%", "Humidity")
                    WeatherStatDark("100%", "Rain")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Tabs
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            DarkTab("Today", true)
            DarkTab("Tomorrow", false)
            DarkTab("Next 3 Days", false)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Forecast Hourly
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("10 am" to "16°", "11 am" to "17°", "12 pm" to "18°", "01 pm" to "19°").forEach {
                HourForecastItemDark(it.first, it.second)
            }
        }
    }
}

@Composable
fun Card(
    backgroundColor: Color,
    shape: RoundedCornerShape,
    elevation: Dp,
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    TODO("Not yet implemented")
}

@Composable
fun WeatherStatDark(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, color = Color.White, fontWeight = FontWeight.Bold)
        Text(text = label, color = Color(0xFFB0B0B0), fontSize = 12.sp)
    }
}

@Composable
fun DarkTab(label: String, selected: Boolean) {
    Text(
        text = label,
        color = if (selected) Color.White else Color.Gray,
        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
        fontSize = 14.sp
    )
}

@Composable
fun HourForecastItemDark(time: String, temp: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(time, color = Color.Gray, fontSize = 12.sp)
        Icon(
            imageVector = Icons.Default.Face, // Replace with your icon
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
        )
        Text(temp, color = Color.White, fontWeight = FontWeight.Bold)
    }
}
