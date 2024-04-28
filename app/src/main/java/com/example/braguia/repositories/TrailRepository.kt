package com.example.braguia.repositories

import com.example.braguia.model.Edge
import com.example.braguia.model.EdgeDB
import com.example.braguia.model.Pin
import com.example.braguia.model.PinDB
import com.example.braguia.model.RelTrail
import com.example.braguia.model.Trail
import com.example.braguia.model.TrailDB
import com.example.braguia.model.dao.EdgeDBDAO
import com.example.braguia.model.dao.RelTrailDAO
import com.example.braguia.model.dao.TrailDBDAO
import com.example.braguia.network.API
import kotlinx.coroutines.flow.Flow

class TrailRepository(
    val API: API,
    val trailDAO: TrailDBDAO,
    val edgeDBDAO: EdgeDBDAO,
    val relTrailDAO: RelTrailDAO,
    val pinRepository: PinRepository
) {
    suspend fun fetchAPI(){
        val trailList = API.getTrails()

        val trailListDB = listOf<TrailDB>().toMutableList()
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
        pinRepository.insert(pinList)
        edgeDBDAO.insert(edgeListDB)
    }

    fun getTrailsPreview(): Flow<List<TrailDB>> {
    //suspend fun getTrailsPreview(): List<TrailDB> {
        return trailDAO.getTrails()
    }

    suspend fun getRelTrails(trailId: Long): List<RelTrail> {
        return relTrailDAO.getRelTrail(trailId)
    }

    //TODO rever
    suspend fun getEdges(trailId: Long): List<Edge> {
        val edgesDB: List<EdgeDB> = edgeDBDAO.getEdges(trailId)
        val edges: MutableList<Edge> = listOf<Edge>().toMutableList()
        for (edgeDB in edgesDB) {
            //TODO estou a assumir um par aqui!!!
            val pins: List<Pin> = pinRepository.getPins(edgeDB.edgeStart,edgeDB.edgeEnd)
            edges.add(edgeDB.toEdge(pins[0],pins[1]))

        }

        return edges
    }


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

    fun EdgeDB.toEdge(edgeStart: Pin, edgeEnd: Pin) = Edge(
        id = id,
        edgeTransport = edgeTransport,
        edgeDuration = edgeDuration,
        edgeDesc = edgeDesc,
        edgeTrail = edgeTrail,
        edgeStart = edgeStart,
        edgeEnd = edgeEnd
    )

}
