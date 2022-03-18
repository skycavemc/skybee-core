package de.leonheuer.skycave.skybeecore.util

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import de.leonheuer.skycave.skybeecore.enums.FarmWorld
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

object PortalUtils {

    private val main = JavaPlugin.getPlugin(SkyBeeCore::class.java)

    fun teleportToMVWorld(player: Player, world: String) : Boolean {
        val mvWorld = main.worldManager.getMVWorld(world) ?: return false
        player.teleport(mvWorld.spawnLocation)
        return true
    }
    
    fun teleportToFarmWorld(player: Player, world: FarmWorld) : Boolean {
        val worldName = getFarmWorldName(world) ?: return false
        player.teleport(main.worldManager.getMVWorld(worldName).spawnLocation)
        return true
    }

    fun setFarmWorldSpawn(world: FarmWorld, loc: Location) {
        main.settings["${world.configName}.world"] = loc.world.name
        main.worldManager.getMVWorld(loc.world).spawnLocation = loc
        save()
    }

    fun setFarmWorldLocked(world: FarmWorld, locked: Boolean) {
        main.settings["${world.configName}.locked"] = locked
        save()
    }

    fun isInFarmWorld(player: Player, world: FarmWorld? = null): Boolean {
        if (world == null) {
            return isInFarmWorld(player, FarmWorld.NETHER) || isInFarmWorld(player, FarmWorld.END)
        }
        return player.world.name.equals(
            main.settings.getString("${world.configName}.world"), true)
    }

    fun isFarmWorldLocked(world: FarmWorld): Boolean {
        return main.settings.getBoolean("${world.configName}.locked")
    }

    fun getFarmWorldName(world: FarmWorld): String? {
        return main.settings.getString("${world.configName}.world")
    }

    private fun save() {
        try {
            main.settings.save(File(main.dataFolder, "settings.yml"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}