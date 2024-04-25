package com.example.braguia.repositories

import com.example.braguia.model.Media
import com.example.braguia.model.Pin
import com.example.braguia.model.PinDB
import com.example.braguia.model.RelPin
import kotlinx.coroutines.coroutineScope
/*

class PinRepository(
    val pinDBDAO: PinDBDAO,
    val relPinDAO: RelPinDAO,
    val mediaRepository: MediaRepository
) {


    suspend fun getPins(pins: List<Pin>) {

        val mediaList = listOf<Media>().toMutableList()
        val pinDBList = listOf<PinDB>().toMutableList()

        for (pin in pins) {

            mediaList.addAll(pin.media)
            pinDBList.add(pin.toPinDB())
        }

        pinDBDAO.insert(pinDBList)

        val pinDBList2 = pinDBDAO.getPin()

    }


    fun Pin.toPinDB() = PinDB(

        id = id,
        pinName = pinName,
        pinDesc = pinDesc,
        pinLat = pinLat,
        pinLng = pinLng,
        pinAlt = pinAlt,

        )

    fun PinDB.toPin(relPin:List<RelPin>,media: List<Media>) = Pin(
        id = id,
        pinName = pinName,
        pinDesc = pinDesc,
        pinLat = pinLat,
        pinLng = pinLng,
        pinAlt = pinAlt,
        relPin = relPin,
        media = media
    )

}*/
