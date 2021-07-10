package de.leonheuer.skycave.skybeecore.util

import java.util.concurrent.TimeUnit

object TimeUtil {

    fun realTimeToTicks(unit: TimeUnit, amount: Long): Long {
        return unit.toSeconds(amount) * 20
    }

}