package com.example.braguia.repositories

import com.example.braguia.model.Edge
import com.example.braguia.model.EdgeDB
import com.example.braguia.model.Trail
import com.example.braguia.model.TrailDB
import com.example.braguia.network.API

//TODO Falta encapsular isto tudo no Call
class TrailRepository(
    val API: API,
    /*    val trailDAO: TrailDBDAO,
        val edgeDBDAO: EdgeDBDAO,
        val relTrailDAO: RelTrailDAO,
        val pinRepository: PinRepository*/
) {


    suspend fun getTrails(): List<Trail> {

        val trailList = API.getTrails()

        /*val trailListDB = listOf<TrailDB>().toMutableList()
        val pinList = listOf<Pin>().toMutableList()
        val edgeListDB = listOf<EdgeDB>().toMutableList()
        val relTrailList = listOf<RelTrail>().toMutableList()

        for (trail in trailList) {

            trailListDB.add(trail.toTrailDB())

            relTrailList.addAll(trail.relTrail)

            val edges = trail.edges

            for (edge in edges) {

                edgeListDB.add(edge.toEdgeDB())
                pinList.add(edge.edgeStart)
                pinList.add(edge.edgeEnd)

            }

        }

        trailDAO.insert(trailListDB)
        relTrailDAO.insert(relTrailList)
        edgeDBDAO.insert(edgeListDB)*/


        return trailList
    }

    //suspend fun getContent(): List<Media> = trailAPI.getContent()


    fun Trail.toTrailDB() = TrailDB(
        id = id,
        trailImg = trailImg,
        trailName = trailName,
        trailDesc = trailDesc,
        trailDuration = trailDuration,
        trailDifficulty = trailDifficulty
    )

    fun Edge.toEdgeDB() = EdgeDB(

        id = id,
        edgeTransport = edgeTransport,
        edgeDuration = edgeDuration,
        edgeDesc = edgeDesc,
        edgeTrail = edgeTrail,
        edgeStart = edgeStart.id,
        edgeEnd = edgeEnd.id

        )

}
