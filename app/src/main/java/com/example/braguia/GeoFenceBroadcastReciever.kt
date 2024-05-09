package com.example.braguia

import android.Manifest
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.braguia.utils.CHANNEL_ID
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        Log.i("GEOFENCEBROADCAST", "geofence triggered")
        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceStatusCodes
                .getStatusCodeString(geofencingEvent.errorCode)
            Log.e("GEOFENCEBROADCAST", errorMessage)
            return
        }
        // Get the transition type.
        val geofenceTransition = geofencingEvent.geofenceTransition
        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            val triggeringGeofences = geofencingEvent.triggeringGeofences

            // get pin
            // Get the transition details as a String.
            // Send notification with pin details
            if (context != null) {
                val pinId = triggeringGeofences.first().requestId
                context.getSystemService(NotificationManager::class.java)
                var builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle("You've entered $pinId")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                NotificationManagerCompat.from(context).notify(1, builder.build())
            }

            Log.i("GEOFENCEBROADCAST", "geofence entered")
        } else {
            // Log the error.
            Log.e("GEOFENCEBROADCAST", geofenceTransition.toString())
        }
    }
}
