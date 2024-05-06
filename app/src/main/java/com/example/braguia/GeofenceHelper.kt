package com.example.braguia

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.braguia.model.Pin
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

//class GeofenceHelper {
//    companion object {
//        private lateinit var geofencingClient: GeofencingClient
//
//        private
//        fun setupGeofencingClient(c: Context) {
//            // request push notifications and location permissions
//            geofencingClient = LocationServices.getGeofencingClient(c)
//        }
//
//        fun getGeofencingRequest(locations: List<Pin>): GeofencingRequest{
//            val geofenceList: List<Geofence> = mutableListOf()
//
//            val pin = locations[0]
//            for (pin in locations) {
//                geofenceList.add(
//                    Geofence.Builder()
//                        .setRequestId(pin.id.toString())
//                        .setCircularRegion(pin.pinLat, pin.pinLng, 150f)
//                        .setExpirationDuration(Geofence.NEVER_EXPIRE)
//                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
//                        .build()
//                )
//            }
//            return GeofencingRequest.Builder().apply {
//                setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
//                addGeofences(geofenceList)
//            }.build()
//        }
//
//        fun addGeofences(geofences: List<GeofencingRequest>,geofencePendingIntent: Intent){
//            if (ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return
//            }
//            geofencingClient.addGeofences(geofences,geofencePendingIntent).run {
//
//            }
//
//        }
//
//
//    }
//}