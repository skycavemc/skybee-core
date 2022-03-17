package de.leonheuer.skycave.skybeecore.listener.player

import de.leonheuer.skycave.skybeecore.util.DisplayUtil
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerLeave : Listener {

    @Suppress("Deprecation")
    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        event.quitMessage = null
        Bukkit.getOnlinePlayers().forEach { DisplayUtil.removePlayerFromScoreboard(it, event.player) }
    }

    @Suppress("Deprecation", "UnstableApiUsage")
    @EventHandler
    fun onPlayerKick(event: PlayerKickEvent) {
        event.leaveMessage = ""
    }

}