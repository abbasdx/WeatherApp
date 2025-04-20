package com.example.weatherapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.weatherapp.api.NetworkResponse
import com.example.weatherapp.api.WeatherModel
import com.example.weatherapp.viewmodel.AuthViewModel
import com.example.weatherapp.viewmodel.WeatherViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun WeatherPage(
    navController: NavHostController,
    viewModel: WeatherViewModel,
    authViewModel: AuthViewModel
) {

    var city by remember {
        mutableStateOf("")
    }


    val weatherResult = viewModel.weatherResult.observeAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                authViewModel.logout()
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true } // Clears backstack
                }
            }) {
                AsyncImage(
                    model = "https://toppng.com/uploads/preview/logout-icon-png-transparent-login-logout-icon-11562923416nzkie6fbka.png",
                    contentDescription = "logout image",
                    modifier = Modifier.size(30.dp)
                )
            }
//            Column {
//                Text(
//                    text = "Welcome home,",
//                    color = secondaryTextColor,
//                    fontSize = 10.sp
//                )
//                Text(
//                    text = "Abbas",
//                    color = secondaryTextColor,
//                    fontSize = 20.sp
//                )
//            }


            OutlinedTextField(
                value = city,
                singleLine = true,
                shape = RoundedCornerShape(30.dp),
                label = { Text(text = "Search City") },
                placeholder = { Text(text = "Search") },
                onValueChange = {
                    city = it
                }, trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search, contentDescription = "Search",
                        modifier = Modifier.clickable(
                            enabled = city.isBlank().not(), onClick = {
                                viewModel.getData(city)
                            })
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 9.dp)
            )
//
        }


        when (val result = weatherResult.value) {
            is NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }

            is NetworkResponse.Error -> {
                Text(text = "Error: ${result.message}")
            }

            is NetworkResponse.Success<*> -> {
                WeatherDetails(data = result.data as WeatherModel)
            }

            null -> {
                Text(text = "Search for a city to get weather info.")
            }
        }
    }

}

@Composable
fun WeatherDetails(data: WeatherModel) {

    val originalDate = data.location.localtime?.split(" ")?.get(0) // e.g. "2025-08-04"
    val formattedDate = if (originalDate != null) {
        val parsedDate = LocalDate.parse(originalDate)
        parsedDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH))
    } else {
        ""
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location icon",
                modifier = Modifier.size(40.dp)
            )
            Text(text = data.location.name.toString(), fontSize = 30.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = data.location.country.toString(), fontSize = 18.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(),
            shape = RoundedCornerShape(45.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WeatherKeyVal("Local Date", formattedDate)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = " ${data.current.temp_c}°C",
                        fontSize = 56.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.DarkGray
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.wrapContentHeight()
                ) {
                    AsyncImage(
                        modifier = Modifier.size(120.dp), // Reduced size for better proximity
                        model = "https:${data.current.condition?.icon}".replace("64x64", "128x128"),
                        contentDescription = "Condition icon"
                    )
                    Spacer(modifier = Modifier.height(4.dp)) // Smaller gap
                    Text(
                        text = data.current.condition?.text.orEmpty(),
                        fontSize = 25.sp, // Slightly smaller to match the icon
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )
                }


            }
        }


        Spacer(modifier = Modifier.height(16.dp))
        Card(
            shape = RoundedCornerShape(45.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("Humidity", data.current.humidity.toString())
                    WeatherKeyVal("Wind Speed", data.current.wind_kph + " km/h")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("UV", data.current.uv.toString())
                    WeatherKeyVal("Participation", data.current.precip_mm + " mm")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("Local Time", data.location.localtime?.split(" ")[1].toString())
                    WeatherKeyVal("Local Date", data.location.localtime?.split(" ")[0].toString())
                }
            }
        }


    }

}

@Composable
fun WeatherKeyVal(key: String, value: String) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
        Text(text = key, fontWeight = FontWeight.SemiBold, color = Color.Gray)
    }
}