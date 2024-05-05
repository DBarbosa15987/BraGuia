package com.example.braguia

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    // ...
    override fun onReceive(context: Context?, intent: Intent?) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
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
            triggeringGeofences.first().requestId
            // Get the transition details as a String.
            // Send notification with pin details
//            sendNotification(geofenceTransitionDetails)

//            Log.i("GEOFENCEBROADCAST", )
        } else {
            // Log the error.
            Log.e( "GEOFENCEBROADCAST", geofenceTransition.toString())
        }
    }
}
