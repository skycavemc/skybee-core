package de.leonheuer.skycave.skybeecore.listener.player

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import de.leonheuer.skycave.skybeecore.enums.FarmWorld
import de.leonheuer.skycave.skybeecore.enums.Message
import de.leonheuer.skycave.skybeecore.util.PortalUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class PlayerMove(private val main: SkyBeeCore): Listener {

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player
        val pm = main.playerManager

        if (PortalUtils.isInFarmWorld(player, FarmWorld.NETHER) &&
            player.location.y > 127 &&
            !player.hasPermission("skybee.core.bypass.farmworlds")
        ) {
            player.sendMessage(Message.FORBIDDEN_AREA.getString().get())
            PortalUtils.teleportToMVWorld(player, "Builder")
            return
        }

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
        }
    }

}