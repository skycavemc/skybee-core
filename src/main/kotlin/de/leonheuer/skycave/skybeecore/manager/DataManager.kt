package de.leonheuer.skycave.skybeecore.manager

import de.leonheuer.skycave.skybeecore.model.User
import de.leonheuer.skycave.skybeecore.util.DataUtil
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap

class DataManager {

    private val registeredUserMap = HashMap<UUID, User>()

    fun registerUser(player: Player) {
        val uuid = player.uniqueId
        val user = DataUtil.getUser(uuid)
        if (user == null) {
            registeredUserMap[uuid] = DataUtil.createAndGetUser(player)
            return
        }
        registeredUserMap[uuid] = user
    }

    fun unregisterUser(uuid: UUID) {
        val user = registeredUserMap[uuid]
        if (user != null) {
            DataUtil.saveUser(user)
        }
        registeredUserMap.remove(uuid)
    }

    fun unregisterAllUsers() {
        registeredUserMap.keys.forEach(this::unregisterUser)
    }

    fun isRegistered(uuid: UUID): Boolean {
        return registeredUserMap.contains(uuid)
    }

    fun getRegisteredUser(player: Player): User {
        return registeredUserMap[player.uniqueId]!!
    }

}