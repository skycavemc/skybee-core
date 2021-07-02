package de.leonheuer.skycave.skybeecore.listener

import com.google.common.io.ByteStreams
import de.leonheuer.skycave.skybeecore.SkyBeeCore
import de.leonheuer.skycave.skybeecore.util.DisplayUtil
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.time.LocalDateTime

class PlayerLeaveListener(private val main: SkyBeeCore): Listener {

    @Suppress("Deprecation")
    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        event.quitMessage = null
        /*main.dataManager.getRegisteredUser(event.player).lastLeft = LocalDateTime.now()
        main.dataManager.unregisterUser(event.player.uniqueId)*/
        Bukkit.getOnlinePlayers().forEach { DisplayUtil.removePlayerFromScoreboard(it, event.player) }
    }

    @Suppress("Deprecation", "UnstableApiUsage")
    @EventHandler
    fun onPlayerKick(event: PlayerKickEvent) {
        event.leaveMessage = ""
        /*event.isCancelled = true

        main.dataManager.getRegisteredUser(event.player).lastLeft = LocalDateTime.now()
        main.dataManager.unregisterUser(event.player.uniqueId)

        var out = ByteStreams.newDataOutput()
        out.writeUTF("Connect")
        out.writeUTF("lobby")
        event.player.sendPluginMessage(main, "BungeeCord", out.toByteArray())

        out = ByteStreams.newDataOutput()
        out.writeUTF("Message")
        out.writeUTF(event.player.name)
        out.writeUTF(ChatColor.translateAlternateColorCodes(
            '&', "&e&l| &6Lobby &8» &cDu wurdest von SkyBlock gekickt! Grund: &e" + event.reason))
        event.player.sendPluginMessage(main, "BungeeCord", out.toByteArray())*/
        //Bukkit.getOnlinePlayers().forEach { DisplayUtil.removePlayerFromScoreboard(it, event.player) }
    }

}