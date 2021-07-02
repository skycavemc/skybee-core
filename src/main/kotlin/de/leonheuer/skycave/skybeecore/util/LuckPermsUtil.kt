package de.leonheuer.skycave.skybeecore.util

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import net.luckperms.api.model.group.Group
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import kotlin.math.absoluteValue

object LuckPermsUtil {

    private val main = JavaPlugin.getPlugin(SkyBeeCore::class.java)
    private val luckPerms = main.luckPerms

    fun getUserGroup(player: Player): Group? {
        val user = luckPerms.getPlayerAdapter(Player::class.java).getUser(player)
        return luckPerms.groupManager.getGroup(user.primaryGroup)
    }

    fun getGroupTabListName(group: Group): String {
        // inversion of group weight: highest group should be at top
        val weight = (group.weight.asInt - SkyBeeCore.PERMISSION_GROUPS_MAX_WEIGHT).absoluteValue
        return weight.toString().padStart(5, '0') + group.name
    }

    fun getPrefix(group: Group): String? {
        return group.cachedData.metaData.prefix
    }

}