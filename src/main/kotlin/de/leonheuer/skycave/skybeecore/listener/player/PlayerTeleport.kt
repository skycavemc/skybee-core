package de.leonheuer.skycave.skybeecore.listener.player

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent

class PlayerTeleport(private val main: SkyBeeCore): Listener {

    @EventHandler
    fun onTeleport(event: PlayerTeleportEvent) {
        main.playerManager.fromLocation[event.player] = event.from
        main.playerManager.toLocation[event.player] = event.to
    }

}