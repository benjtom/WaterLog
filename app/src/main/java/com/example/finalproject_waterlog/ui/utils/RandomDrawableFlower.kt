package com.example.finalproject_waterlog.ui.utils

import com.example.finalproject_waterlog.R

class RandomDrawableFlower {
    companion object {

        fun getRandomRarity(): Pair<Int, String> {
            val random = (1..100).random()
            var flowerTuple = Pair<Int, String>(0, "")
            flowerTuple = when (random) {
                in 1 .. 50 -> getRandomCommonFlower()
                in 51 .. 80 -> getRandomUncommonFlower()
                in 81 .. 95 -> getRandomRareFlower()
                in 96 .. 100 -> getRandomLegendaryFlower()
                else -> getRandomCommonFlower()
            }
            return flowerTuple
        }


        fun getRandomCommonFlower(): Pair<Int, String> {
            val random = (1..7).random()
            var flowerTuple = Pair<Int, String>(0, "")
            flowerTuple = when (random) {
                1 -> Pair<Int, String>(R.drawable.yellow, "yellow")
                2 -> Pair<Int, String>(R.drawable.orange, "orange")
                3 -> Pair<Int, String>(R.drawable.purple, "purple")
                4 -> Pair<Int, String>(R.drawable.pink, "pink")
                5 -> Pair<Int, String>(R.drawable.red, "red")
                6 -> Pair<Int, String>(R.drawable.sun, "sun")
                7 -> Pair<Int, String>(R.drawable.tulip, "tulip")
                else -> Pair<Int, String>(R.drawable.yellow, "yellow")
            }
            return flowerTuple
        }

        fun getRandomUncommonFlower(): Pair<Int, String> {
            val random = (1..8).random()
            var flowerTuple = Pair<Int, String>(0, "")
            flowerTuple = when (random) {
                1 -> Pair<Int, String>(R.drawable.blue, "blue")
                2 -> Pair<Int, String>(R.drawable.carnation, "carnation")
                3 -> Pair<Int, String>(R.drawable.coral, "coral")
                4 -> Pair<Int, String>(R.drawable.gold, "gold")
                5 -> Pair<Int, String>(R.drawable.iris, "iris")
                6 -> Pair<Int, String>(R.drawable.poppy, "poppy")
                7 -> Pair<Int, String>(R.drawable.rose, "rose")
                8 -> Pair<Int, String>(R.drawable.salmon, "salmon")
                else -> Pair<Int, String>(R.drawable.gold, "gold")
            }
            return flowerTuple
        }

        fun getRandomRareFlower(): Pair<Int, String> {
            val random = (1..5).random()
            var flowerTuple = Pair<Int, String>(0, "")
            flowerTuple = when (random) {
                1 -> Pair<Int, String>(R.drawable.achimenes, "achimenes")
                2 -> Pair<Int, String>(R.drawable.comet, "comet")
                3 -> Pair<Int, String>(R.drawable.geneva, "geneva")
                4 -> Pair<Int, String>(R.drawable.polaris, "polaris")
                5 -> Pair<Int, String>(R.drawable.starry, "starry")
                else -> Pair<Int, String>(R.drawable.achimenes, "achimenes")
            }
            return flowerTuple
        }

        fun getRandomLegendaryFlower(): Pair<Int, String> {
            val random = (1..3).random()
            var flowerTuple = Pair<Int, String>(0, "")
            flowerTuple = when (random) {
                1 -> Pair<Int, String>(R.drawable.rainbow, "rainbow")
                2 -> Pair<Int, String>(R.drawable.nebula, "nebula")
                3 -> Pair<Int, String>(R.drawable.midnight, "midnight")
                else -> Pair<Int, String>(R.drawable.rainbow, "rainbow")
            }
            return flowerTuple
        }
    }
}