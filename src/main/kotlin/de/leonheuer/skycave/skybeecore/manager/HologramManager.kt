package de.leonheuer.skycave.skybeecore.manager

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType

class HologramManager(private val main: SkyBeeCore) {

    private val hologramMap = HashMap<String, ArmorStand>()

    fun createTempHologram(location: Location, text: String, ticks: Long) {
        val holo = location.world.spawnEntity(location, EntityType.ARMOR_STAND) as ArmorStand
        holo.setGravity(false)
        holo.canPickupItems = false
        holo.customName = ChatColor.translateAlternateColorCodes('&', text)
        holo.isCustomNameVisible = true
        holo.isVisible = false
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, { holo.remove() }, ticks)
    }

}