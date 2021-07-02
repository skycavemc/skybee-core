package de.leonheuer.skycave.skybeecore.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerArmorStandManipulateEvent

class HologramProtectionListener: Listener {

    @EventHandler
    fun onPlayerArmorStandManipulate(event: PlayerArmorStandManipulateEvent) {
        if (!event.rightClicked.isVisible) {
            event.isCancelled = true
        }
    }

}