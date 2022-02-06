package de.leonheuer.skycave.skybeecore.listener.player

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerDeath(private val main: SkyBeeCore): Listener {

    @Suppress("Deprecation")
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        event.deathMessage = null
        main.dataManager.getRegisteredUser(event.entity).deaths.inc()
    }

}