package com.example.finalproject_waterlog.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject_waterlog.WaterLogApplication
import com.example.finalproject_waterlog.repositories.UserInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateUserScreenViewModel(
    private val userInfoRepository: UserInfoRepository
): ViewModel() {
    private val _name = MutableStateFlow("")
    private val _weight = MutableStateFlow(0)
    private val _ouncesDrunk = MutableStateFlow(0)

    val name: StateFlow<String> = _name
    val weight: StateFlow<Int> = _weight

    fun setName(name: String) {
        _name.value = name
    }

    fun setWeight(weight: Int) {
        _weight.value = weight
    }

    fun setOuncesDrunk(ouncesDrunk: Int) {
        _ouncesDrunk.value = ouncesDrunk
    }

    suspend fun saveUserInfo() {
        if (_name.value.isNotEmpty() && _weight.value > 0) {
            userInfoRepository.setUserInfo(
                _name.value,
                _weight.value,
                _ouncesDrunk.value,
            )

            userInfoRepository.loadUserInfo()
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as WaterLogApplication
                CreateUserScreenViewModel(
                    application.userInfoRepository
                )
            }
        }
    }
}