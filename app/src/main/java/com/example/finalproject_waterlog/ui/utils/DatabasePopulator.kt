package com.example.finalproject_waterlog.ui.utils

import com.example.finalproject_waterlog.WaterLogApplication
import com.example.finalproject_waterlog.repositories.DrinkLogRepository
import com.example.finalproject_waterlog.repositories.FlowerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

/**
 * Utility class to populate the database with example data.
 * This can be used for testing or to provide users with sample data.
 */
class DatabasePopulator(private val application: WaterLogApplication) {

    private val drinkLogRepository: DrinkLogRepository = application.drinkLogRepository
    private val flowerRepository: FlowerRepository = application.flowerRepository
    private val scope = CoroutineScope(Dispatchers.IO)

    /**
     * Populates the drink logs database with 5 drink logs for each of the past 7 days.
     * Each day will have 5 random drink logs with random ounce amounts.
     */
    fun populateDrinkLogs() {
        scope.launch {
            // Clear existing drink logs first
            drinkLogRepository.deleteDrinkLogs()
            
            val calendar = Calendar.getInstance()
            val random = java.util.Random()
            
            // For each of the past 6 days
            for (dayOffset in 1..5) {
                // Set the date to the current date minus the day offset
                calendar.time = Date()
                calendar.add(Calendar.DAY_OF_YEAR, -dayOffset)
                
                // Create 5 drink logs for this day
                for (i in 1..5) {
                    // Random ounce amount between 4 and 16
                    val ounces = listOf(1, 2, 4).random() * 4
                    
                    // Set the time to a random hour in the day
                    val hour = random.nextInt(24)
                    val minute = random.nextInt(60)
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    
                    // Add the drink log
                    drinkLogRepository.addDrinkLog(calendar.time, ounces)
                }
            }
        }
    }
    
    /**
     * Populates the flowers database with 7 random flowers.
     */
    fun populateFlowers() {
        scope.launch {
            // Clear existing flowers first
            flowerRepository.deleteFlowers()
            
            // Add 7 random flowers
            for (i in 1..7) {
                val flowerTuple = RandomDrawableFlower.getRandomRarity()
                flowerRepository.addFlower(flowerTuple.second)
            }
        }
    }
    
    /**
     * Populates both databases with example data.
     */
    fun populateAllDatabases() {
        populateDrinkLogs()
        populateFlowers()
    }
} 