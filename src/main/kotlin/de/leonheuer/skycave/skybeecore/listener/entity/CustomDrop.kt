package de.leonheuer.skycave.skybeecore.listener.entity

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import de.leonheuer.skycave.skybeecore.util.CustomDropUtil
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

class CustomDrop(private val main: SkyBeeCore): Listener {

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        val customDrop = CustomDropUtil.getRandomCustomDrop(event.entityType)
        if (customDrop != null) {
            main.hologramManager.createTempHologram(event.entity.location, customDrop.text, 30L)
            event.drops.add(customDrop.itemStack)
        }
    }

}