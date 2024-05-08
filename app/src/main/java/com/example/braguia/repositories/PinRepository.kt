package com.example.braguia.repositories

import com.example.braguia.model.Media
import com.example.braguia.model.Pin
import com.example.braguia.model.PinDB
import com.example.braguia.model.RelPin
import com.example.braguia.model.dao.PinDBDAO
import com.example.braguia.model.dao.RelPinDAO

class PinRepository(
    val pinDBDAO: PinDBDAO,
    val relPinDAO: RelPinDAO,
    val mediaRepository: MediaRepository
) {
    suspend fun getAllPins(): List<PinDB>{
        return pinDBDAO.getAllPins()
    }
    suspend fun insert(pins: List<Pin>) {

        val mediaList = listOf<Media>().toMutableList()
        val pinDBList = listOf<PinDB>().toMutableList()
        val relPinList = listOf<RelPin>().toMutableList()

        for (pin in pins) {
            mediaList.addAll(pin.media)
            relPinList.addAll(pin.relPin)
            pinDBList.add(pin.toPinDB())
        }
        pinDBDAO.insert(pinDBList)
        relPinDAO.insert(relPinList)
        mediaRepository.insert(mediaList)
    }

    suspend fun getRelPins(pinId:Long): List<RelPin>{
        return relPinDAO.getRelPin(pinId)
    }

    suspend fun getPin(pinId: Long):Pin?{
        val pinDB:PinDB? = pinDBDAO.getPin(pinId)
        var pin:Pin? = null
        if (pinDB != null) {
            val relPins: List<RelPin> = this.getRelPins(pinDB.id)
            val media: List<Media> = mediaRepository.getMedia(pinDB.id)
            pin = pinDB.toPin(relPins,media)
        }
        return pin
    }

    suspend fun getPinMedia(pinId: Long){
        mediaRepository.getMedia(pinId)
    }

    fun Pin.toPinDB() = PinDB(

        id = id,
        pinName = pinName,
        pinDesc = pinDesc,
        pinLat = pinLat,
        pinLng = pinLng,
        pinAlt = pinAlt,

        )

    fun PinDB.toPin(relPin: List<RelPin>, media: List<Media>) = Pin(
        id = id,
        pinName = pinName,
        pinDesc = pinDesc,
        pinLat = pinLat,
        pinLng = pinLng,
        pinAlt = pinAlt,
        relPin = relPin,
        media = media
    )
}
