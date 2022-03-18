package de.leonheuer.skycave.skybeecore.listener.misc

import de.leonheuer.skycave.skybeecore.enums.FarmWorld
import de.leonheuer.skycave.skybeecore.util.PortalUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.PortalCreateEvent

class PortalCreate : Listener {

    @EventHandler
    fun onPortalCreate(event: PortalCreateEvent) {
        if (event.world.name == PortalUtils.getFarmWorldName(FarmWorld.NETHER)) {
            event.isCancelled = true
        }
    }

}