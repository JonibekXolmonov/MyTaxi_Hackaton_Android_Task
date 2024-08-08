package uz.mobile.mytaxitask

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.*
import uz.mobile.mytaxitask.common.service.LocationService
import uz.mobile.mytaxitask.presentation.ui.screens.home.HomeScreen
import uz.mobile.mytaxitask.presentation.ui.theme.MyTaxiAndroidTaskTheme
import kotlin.properties.Delegates

class MainActivity : ComponentActivity() {

    private lateinit var locationManager: LocationManager

    private var isGpsEnabled by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        setContent {
            MyTaxiAndroidTaskTheme {
                HomeScreen(Modifier.fillMaxSize())
            }
        }

        requestLocationPermission()
    }

    private fun requestLocationPermission() {
        if (
            checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (isGpsEnabled) {
                startLocationTrackingService()
            } else {
                openGpsSettings()
            }
        } else {
            locationPermissionRequest.launch(locationPermissions)
        }
    }


    private val locationPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.POST_NOTIFICATIONS
        )
    } else {
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        val accessFine = permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)

        val accessCourse =
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)

        val notificationPost = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.getOrDefault(Manifest.permission.POST_NOTIFICATIONS, false)
        } else {
            true
        }

        if ((accessCourse || accessFine) && notificationPost) {
            if (isGpsEnabled) {
                startLocationTrackingService()
            } else {
                openGpsSettings()
            }
        }
    }

    private fun openGpsSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        resultLauncher.launch(intent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Toast.makeText(this, "$result", Toast.LENGTH_SHORT).show()
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (isGpsEnabled) {
                startLocationTrackingService()
            } else {
                openGpsSettings()
            }
        }

    private fun startLocationTrackingService() {
        Intent(this, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            startService(this)
        }
    }

    private fun stopLocationTrackingService() {
        Intent(this, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP
            stopService(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        stopLocationTrackingService()
    }
}