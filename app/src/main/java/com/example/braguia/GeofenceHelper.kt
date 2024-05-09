package com.example.braguia

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.braguia.model.PinDB
import com.example.braguia.utils.CUSTOM_INTENT_GEOFENCE
import com.example.braguia.utils.CUSTOM_REQUEST_CODE_GEOFENCE
import com.example.braguia.utils.GEOFENCE_RADIUS
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices


class GeofenceHelper(context: Context) {
    private val TAG = "GEOFENCEHELPER"
    private val geofencingClient = LocationServices.getGeofencingClient(context)
    val geofenceMap = mutableMapOf<String, Geofence>()
    private val geofencingPendingIntent by lazy {
        PendingIntent.getBroadcast(
            context,
            CUSTOM_REQUEST_CODE_GEOFENCE,
            Intent(CUSTOM_INTENT_GEOFENCE),
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                PendingIntent.FLAG_CANCEL_CURRENT
            } else {
                PendingIntent.FLAG_MUTABLE
            }
        )
    }

    // request push notifications and location permissions
    @SuppressLint("VisibleForTests")
    fun getGeofencingRequest(locations: List<PinDB>): GeofencingRequest {
        val geofenceList = mutableListOf<Geofence>()
        for (pin in locations) {
            geofenceList.add(
                Geofence.Builder()
                    .setRequestId(pin.id.toString())
                    .setCircularRegion(pin.pinLat, pin.pinLng, GEOFENCE_RADIUS)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .build()
            )
        }
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            addGeofences(geofenceList)
        }.build()
    }

    fun addGeofences(
        pins: List<PinDB>,
        geofencePendingIntent: PendingIntent,
        context: Context,
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i(TAG, "Permissions not granted to add Geofences")
            return
        }

        val geofenceRequest = getGeofencingRequest(pins)
        Log.d(TAG,geofenceRequest.toString())
        geofencingClient.addGeofences(geofenceRequest, geofencePendingIntent).run {
            addOnSuccessListener {
                Log.i(TAG, "Geofences successfully added")
            }
            addOnFailureListener {
                Log.e(TAG, "Failed to add geofences $it")
            }
        }
    }

    fun removeGeofences(geofencePendingIntent: PendingIntent) {
        geofencingClient.removeGeofences(geofencePendingIntent)?.run {
            addOnSuccessListener {
                Log.i(TAG, "Geofences successfully removed")
            }
            addOnFailureListener {
                Log.e(TAG, "Failed to remove Geofences")
            }
        }
    }
}