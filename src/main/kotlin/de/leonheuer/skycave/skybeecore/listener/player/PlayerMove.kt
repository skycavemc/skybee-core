package de.leonheuer.skycave.skybeecore.listener.player

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class PlayerMove(private val main: SkyBeeCore): Listener {

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player
        val pm = main.playerManager

        if (!pm.fromLocation.containsKey(player)) {
            pm.fromLocation[player] = player.location
        }
        if (!pm.toLocation.containsKey(player)) {
            pm.toLocation[player] = player.location
        }

        val to = pm.toLocation[player] ?: return
        if (player.location.world == to.world &&
            player.location.distance(to) >= 75
        ) {
            pm.fromLocation[player] = to
            pm.toLocation[player] = pm.fromLocation[player] ?: return
        }
    }

}