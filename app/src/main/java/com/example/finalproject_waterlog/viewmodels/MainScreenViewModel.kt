package com.example.finalproject_waterlog.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject_waterlog.R
import com.example.finalproject_waterlog.WaterLogApplication
import com.example.finalproject_waterlog.repositories.DrinkLogRepository
import com.example.finalproject_waterlog.repositories.FlowerRepository
import com.example.finalproject_waterlog.repositories.UserInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class MainScreenViewModel(
    private val userInfoRepository: UserInfoRepository,
    private val drinkLogRepository: DrinkLogRepository,
    private val flowerRepository: FlowerRepository
): ViewModel() {
    private val _drawableFlower = MutableStateFlow(R.drawable.stage1)
    val drawableFlower: StateFlow<Int> = _drawableFlower

    private val _percentComplete = MutableStateFlow(userInfoRepository.userInfo.value.ouncesDrunk.toFloat() /
            (userInfoRepository.userInfo.value.weight.toFloat()/2))
    val percentComplete: StateFlow<Float> = _percentComplete


    fun setDrawableFlower(drawableId: Int) {
        _drawableFlower.value = drawableId
    }

    suspend fun addDrinkLog(ounces: Int) {
        drinkLogRepository.addDrinkLog(
            date = Date(),
            ounces = ounces
        )
    }

    fun setOuncesDrunk(ouncesDrunk: Int) {
        var ouncesDrunkFloat = userInfoRepository.userInfo.value.ouncesDrunk.toFloat()

        viewModelScope.launch {
            userInfoRepository.setOuncesDrunk(ouncesDrunk)
        }

        _percentComplete.value = ouncesDrunkFloat /
                (userInfoRepository.userInfo.value.weight.toFloat()/2)
    }

    fun addFlower(type: String) {
        viewModelScope.launch {
            flowerRepository.addFlower(type)
        }
    }


    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as WaterLogApplication
                MainScreenViewModel(
                    application.userInfoRepository,
                    application.drinkLogRepository,
                    application.flowerRepository
                )
            }
        }
    }

}