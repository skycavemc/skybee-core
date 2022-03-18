package de.leonheuer.skycave.skybeecore.listener.player

import de.leonheuer.skycave.skybeecore.enums.FarmWorld
import de.leonheuer.skycave.skybeecore.enums.Message
import de.leonheuer.skycave.skybeecore.util.PortalUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent

class PlayerChangedWorld : Listener {

    @EventHandler
    fun onWorldChange(event: PlayerChangedWorldEvent) {
        val player = event.player
        if (player.hasPermission("skybee.portals.bypass.locked")) {
            return
        }
        if ((PortalUtils.isInFarmWorld(player, FarmWorld.NETHER) && PortalUtils.isFarmWorldLocked(FarmWorld.NETHER)) ||
            (PortalUtils.isInFarmWorld(player, FarmWorld.END) && PortalUtils.isFarmWorldLocked(FarmWorld.END))
        ) {
            player.sendMessage(Message.DIMENSION_LOCKED.getString().get())
            PortalUtils.teleportToMVWorld(player, "Builder")
        }
    }

}