package com.example.finalproject_waterlog.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject_waterlog.WaterLogApplication
import com.example.finalproject_waterlog.repositories.UserInfoRepository
import com.example.finalproject_waterlog.repositories.FlowerRepository
import com.example.finalproject_waterlog.repositories.DrinkLogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OptionsScreenViewModel(
    private val userInfoRepository: UserInfoRepository,
    private val flowerRepository: FlowerRepository,
    private val drinkLogRepository: DrinkLogRepository
): ViewModel() {
    private val _weight = MutableStateFlow(0)
    val weight: StateFlow<Int> = _weight

    fun setWeight(weight: Int) {
        _weight.value = weight
    }

    suspend fun updateWeight() {
        if (_weight.value > 0) {
            userInfoRepository.setWeight(_weight.value)
        }
    }

    suspend fun deleteUserData() {
        userInfoRepository.deleteUserInfo()
        flowerRepository.deleteFlowers()
        drinkLogRepository.deleteDrinkLogs()
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as WaterLogApplication
                OptionsScreenViewModel(
                    application.userInfoRepository,
                    application.flowerRepository,
                    application.drinkLogRepository
                )
            }
        }
    }
} 