package de.leonheuer.skycave.skybeecore.util

import kotlin.math.roundToInt

object RandomUtil {

    fun randomCaptcha(): String {
        return (Math.random() * 1000000).roundToInt().toString()
    }

}