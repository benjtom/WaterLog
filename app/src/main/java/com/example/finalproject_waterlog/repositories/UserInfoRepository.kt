package com.example.finalproject_waterlog.repositories

import com.example.finalproject_waterlog.Destinations
import com.example.finalproject_waterlog.daos.UserInfoDao
import com.example.finalproject_waterlog.models.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserInfoRepository(
    private val userInfoDao: UserInfoDao
) {
    private val _userInfo = MutableStateFlow(UserInfo(
        name = "",
        weight = 0,
        ouncesDrunk = 0
    ))
    private val _userExists = MutableStateFlow(false)
    val userInfo: StateFlow<UserInfo> = _userInfo
    val userExists: StateFlow<Boolean> = _userExists

    suspend fun loadUserInfo() {
        val userInfoFromDao = userInfoDao.getUserInfo()
        if (userInfoFromDao == null) {
            _userInfo.value = UserInfo(
                name = "",
                weight = 0,
                ouncesDrunk = 0
            )
            _userExists.value = false
            return
        } else {
            _userInfo.value = userInfoFromDao
            _userExists.value = true
        }
    }

    suspend fun setUserInfo(
        name: String,
        weight: Int,
        ouncesDrunk: Int
    ) {
        val newUserInfo = UserInfo(
            name = name,
            weight = weight,
            ouncesDrunk = ouncesDrunk
        )
        userInfoDao.upsertUserInfo(newUserInfo)
        _userInfo.value = newUserInfo
    }

    suspend fun setOuncesDrunk(
        ouncesDrunk: Int
    ) {
        val newUserInfo = _userInfo.value.copy(ouncesDrunk = ouncesDrunk)
        userInfoDao.upsertUserInfo(newUserInfo)
        _userInfo.value = newUserInfo
    }

    suspend fun setWeight(
        weight: Int
    ) {
        val newUserInfo = _userInfo.value.copy(weight = weight)
        userInfoDao.upsertUserInfo(newUserInfo)
        _userInfo.value = newUserInfo
    }

    suspend fun deleteUserInfo() {
        userInfoDao.deleteUserInfo(_userInfo.value)
        _userInfo.value = UserInfo(
            name = "",
            weight = 0,
            ouncesDrunk = 0
        )
        _userExists.value = false
    }

}