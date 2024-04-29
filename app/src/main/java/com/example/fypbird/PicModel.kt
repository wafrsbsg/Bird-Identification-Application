package com.example.fypbird

import java.util.*

data class PicModel(
    var id: Int = getAutoId(),
            var bitmap: String = "",
                    var birdname: String = ""

){
    companion object {
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }
}