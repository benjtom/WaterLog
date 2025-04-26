package com.example.finalproject_waterlog.repositories

import com.example.finalproject_waterlog.daos.FlowersDao
import com.example.finalproject_waterlog.models.Flower
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FlowerRepository(
    private val flowersDao: FlowersDao
) {
    private val _flowers = MutableStateFlow(emptyList<Flower>())
    val flowers: StateFlow<List<Flower>> = _flowers

    suspend fun loadFlowers() {
        _flowers.value = flowersDao.getAllFlowers()
    }

    suspend fun addFlower(
        type: String
    ) {
        val newFlower = Flower(
            type = type
        )
        newFlower.id = flowersDao.insertFlower(newFlower)
        _flowers.value += newFlower
    }

    suspend fun deleteFlowers() {
        flowersDao.deleteAllFlowers()
        _flowers.value = emptyList()
    }

}