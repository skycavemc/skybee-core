package de.leonheuer.skycave.skybeecore.listener.player

import de.leonheuer.skycave.skybeecore.util.DisplayUtil
import de.leonheuer.skycave.skybeecore.util.LuckPermsUtil
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoin : Listener {

    @Suppress("Deprecation")
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        player.sendTitle("§8» §6Willkommen §8«", "§7auf §eSky§6Bee §71.18", 10, 40, 20)
        player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.7F)
        event.joinMessage = "§8[§6+§8] §e${player.name} §7hat den Server betreten."

        val prefix = LuckPermsUtil.getPrefix(LuckPermsUtil.getUserGroup(player)!!)!!.replace("&", "§")
        player.setPlayerListName("$prefix §8| §7${player.name}")
        DisplayUtil.setScoreBoard(player)
        Bukkit.getOnlinePlayers().forEach {
            if (it != player) {
                DisplayUtil.addPlayerToScoreboard(it, player)
            }
        }
    }

}