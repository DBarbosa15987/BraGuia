package com.example.braguia

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.braguia.model.PinDB
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices


class GeofenceHelper(context: Context) {
    private val TAG = "GEOFENCEHELPER"
    private val geofencingClient = LocationServices.getGeofencingClient(context)

    // request push notifications and location permissions
    fun getGeofencingRequest(locations: List<PinDB>): GeofencingRequest {
        val geofenceList = mutableListOf<Geofence>()
        for (pin in locations) {
            geofenceList.add(
                Geofence.Builder()
                    .setRequestId(pin.id.toString())
                    .setCircularRegion(pin.pinLat, pin.pinLng, 150f)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .build()
            )
        }
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofences(geofenceList)
        }.build()
    }

    fun addGeofences(
        pins: List<PinDB>,
        geofencePendingIntent: PendingIntent,
        context: Context,
    ) {
        val geofenceRequest = getGeofencingRequest(pins)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        geofencingClient.addGeofences(geofenceRequest, geofencePendingIntent).run {
            addOnSuccessListener {
                Log.i(TAG, "Geofences successfully added")
            }
            addOnFailureListener {
                Log.e(TAG, "Failed to add geofences")
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