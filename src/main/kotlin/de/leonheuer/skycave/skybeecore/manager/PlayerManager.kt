package de.leonheuer.skycave.skybeecore.manager

import de.leonheuer.skycave.skybeecore.models.TemporaryUser
import de.leonheuer.skycave.skybeecore.util.RandomUtil
import org.bukkit.entity.Player

class PlayerManager {

    val vanishList = ArrayList<Player>()
    private val temporaryUserMap = HashMap<Player, TemporaryUser>()

    fun registerUser(player: Player) {
        temporaryUserMap[player] = TemporaryUser(HashMap(), null, RandomUtil.randomCaptcha(), false)
    }

    fun unregisterUser(player: Player) {
        temporaryUserMap.remove(player)
    }

    fun isRegistered(player: Player): Boolean {
        return temporaryUserMap.contains(player)
    }

    fun getRegisteredUser(player: Player): TemporaryUser {
        return temporaryUserMap[player]!!
    }

}