package uz.mobile.mytaxitask.common.service

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import uz.mobile.mytaxitask.R
import uz.mobile.mytaxitask.common.NOTIFICATION_CHANEL_ID
import uz.mobile.mytaxitask.data.model.UserLocation
import uz.mobile.mytaxitask.domain.use_cases.InsertUserLocationUseCase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LocationService : Service() {

    private val serviceScope: CoroutineScope by inject()
    private val locationClient: LocationClient by inject()
    private val insertUserLocationUseCase: InsertUserLocationUseCase by inject()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANEL_ID)
            .setContentTitle("Tracking location...")
            .setContentText("Location: Loading")
            .setSmallIcon(R.drawable.ic_mytaxi)
            .setOngoing(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        locationClient
            .getLocationUpdates(10_000L)
            .catch { e ->
                e.printStackTrace()
            }
            .onEach { location ->
                val latitude = location.latitude
                val longitude = location.longitude
                val updatedNotification = notification.setContentText(
                    "Location: ($latitude, $longitude)"
                )
                notificationManager.notify(1, updatedNotification.build())

                val currentTime: String =
                    SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                val userCurrentLocation =
                    UserLocation(timeStamp = currentTime, latitude = latitude, longitude = longitude)

                insertUserLocationUseCase.invoke(userCurrentLocation)
            }
            .launchIn(serviceScope)

        startForeground(1, notification.build())
    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_DETACH)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}