package com.jasmeet.roadcastAssign.view.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.jasmeet.roadcastAssign.model.location.LocationDetails
import com.jasmeet.roadcastAssign.utils.Utils
import com.jasmeet.roadcastAssign.view.appComponents.TextComponent
import com.jasmeet.roadcastAssign.view.appComponents.TopAppBarComponent


@Composable
fun LocationScreen(
    navController: NavHostController
) {

    var locationCallBack: LocationCallback? = null
    var fusedLocationClient: FusedLocationProviderClient? = null
    val locationRequired = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    val isLoading = remember {
        mutableStateOf(true)
    }

    var currentLocation by remember {
        mutableStateOf(LocationDetails(0.toDouble(), 0.toDouble()))
    }
    val showDialog = remember { mutableStateOf(false) }

    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            locationRequired.value = true
            startLocationUpdates(locationCallBack, fusedLocationClient)
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()

            // Check if we have a valid location before navigating
            fusedLocationClient?.lastLocation?.addOnSuccessListener { lastLocation ->
                if (lastLocation != null && lastLocation.latitude != 0.0 && lastLocation.longitude != 0.0) {
                    isLoading.value = false
                    currentLocation = LocationDetails(lastLocation.latitude, lastLocation.longitude)
                }
            }
        } else {
            showDialog.value = true
        }
    }

    fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    locationCallBack = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            for (lo in p0.locations) {
                currentLocation = LocationDetails(lo.latitude, lo.longitude)
                if (lo.latitude != 0.0 && lo.longitude != 0.0) {
                    isLoading.value = false
                    fusedLocationClient.removeLocationUpdates(this)
                    return
                }
            }
        }
    }


    LaunchedEffect(true) {
        if (permissions.all {
                ContextCompat.checkSelfPermission(
                    context,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            startLocationUpdates(locationCallBack, fusedLocationClient)
        } else {
            launcherMultiplePermissions.launch(permissions)
        }
    }


    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = "Your Location Details are",
                enableBackButton = true,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) {
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(it)
                .fillMaxSize(),

            ) {

            val fullAddress = Utils.getCompleteAddressString(
                currentLocation.latitude,
                currentLocation.longitude,
                context
            )
            TextComponent(
                text = "Latitude: ${currentLocation.latitude} ",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                textSize = 16.5.sp,
            )
            TextComponent(
                text = "Longitude: ${currentLocation.longitude} ",
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                textSize = 16.5.sp,
            )
            TextComponent(
                text = "Your Full Address : $fullAddress",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                textSize = 16.5.sp,
            )


        }
        if (showDialog.value) {
            PermissionDeniedDialog(
                onExit = { /*TODO*/ },
                onNavigateToSettings = {
                    context.startActivity(
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                    )
                    showDialog.value = false
                }

            )
        }
        if (isLoading.value) {
            Dialog(onDismissRequest = {}) {

                Box(contentAlignment = Alignment.Center) {
                    Column(
                        Modifier
                            .wrapContentSize()
                            .background(Color.White, MaterialTheme.shapes.large),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(Modifier.padding(10.dp), color = Color.Black)
                        TextComponent(
                            text = "Fetching your Location",
                            modifier = Modifier.padding(10.dp),
                            textColor = Color.Black
                        )

                    }
                }

            }
        }
    }
}


fun startLocationUpdates(
    locationCallBack: LocationCallback?,
    fusedLocationClient: FusedLocationProviderClient?,
    timeInterval: Long = 7000,
) {
    locationCallBack?.let {
        val locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, timeInterval).apply {
                setMinUpdateDistanceMeters(0.0f)
                setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                setWaitForAccurateLocation(true)
            }.build()
        fusedLocationClient?.requestLocationUpdates(
            locationRequest,
            it,
            Looper.getMainLooper()
        )
    }
}

@Composable
fun PermissionDeniedDialog(onExit: () -> Unit, onNavigateToSettings: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = "Permission Denied") },
        text = { Text("Please enable location permission in settings.") },
        confirmButton = {
            Button(onClick = { onNavigateToSettings() }) {
                Text("Go to Settings")
            }
        },
        dismissButton = {
            Button(onClick = { onExit() }) {
                Text("Exit App")
            }
        }
    )
}

