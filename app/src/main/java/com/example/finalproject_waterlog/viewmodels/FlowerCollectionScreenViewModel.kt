package com.example.finalproject_waterlog.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject_waterlog.WaterLogApplication
import com.example.finalproject_waterlog.models.Flower
import com.example.finalproject_waterlog.repositories.FlowerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FlowerCollectionScreenViewModel(
    private val flowerRepository: FlowerRepository
): ViewModel() {
    private val _flowers = MutableStateFlow(emptyList<Flower>())
    val flowers: StateFlow<List<Flower>> = _flowers

    fun loadFlowers() {
        viewModelScope.launch {
            flowerRepository.loadFlowers()
        }
    }

    init {
        viewModelScope.launch {
            flowerRepository.flowers.collect {
                _flowers.value = it
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as WaterLogApplication
                FlowerCollectionScreenViewModel(
                    application.flowerRepository
                )
            }
        }
    }

}