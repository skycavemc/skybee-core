package de.leonheuer.skycave.skybeecore.listener.player

import de.leonheuer.skycave.skybeecore.enums.FarmWorld
import de.leonheuer.skycave.skybeecore.enums.Message
import de.leonheuer.skycave.skybeecore.util.PortalUtils
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityToggleGlideEvent

class PlayerToggleGlide : Listener {

    @EventHandler
    fun onEntityToggleGlide(event: EntityToggleGlideEvent) {
        val player = event.entity
        if (player !is Player) {
            return
        }

        if (PortalUtils.isInFarmWorld(player, FarmWorld.NETHER)) {
            event.isCancelled = true
            player.sendMessage(Message.FORBIDDEN_FLIGHT.getString().get())
        }
    }

}