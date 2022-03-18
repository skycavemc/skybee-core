package de.leonheuer.skycave.skybeecore.listener.block

import de.leonheuer.skycave.skybeecore.enums.Message
import de.leonheuer.skycave.skybeecore.util.PortalUtils
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class BlockPlace : Listener {

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        if (player.hasPermission("skybee.core.bypass.farmworlds")) {
            return
        }
        if (!PortalUtils.isInFarmWorld(player)) {
            return
        }
        if (event.block.type == Material.SPAWNER || event.block.type == Material.BEE_NEST || event.block.type == Material.BEEHIVE) {
            event.isCancelled = true
            player.sendMessage(Message.FORBIDDEN_BLOCK.getString().get())
        }
    }

}