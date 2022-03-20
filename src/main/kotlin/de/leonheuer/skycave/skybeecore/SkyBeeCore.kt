package de.leonheuer.skycave.skybeecore

import com.onarandombox.MultiverseCore.MultiverseCore
import com.onarandombox.MultiverseCore.api.MVWorldManager
import de.leonheuer.skycave.skybeecore.commands.BackCommand
import de.leonheuer.skycave.skybeecore.commands.PortalsCommand
import de.leonheuer.skycave.skybeecore.commands.WikiCommand
import de.leonheuer.skycave.skybeecore.listener.block.BlockPlace
import de.leonheuer.skycave.skybeecore.listener.inventory.InventoryClick
import de.leonheuer.skycave.skybeecore.listener.misc.PortalCreate
import de.leonheuer.skycave.skybeecore.listener.player.*
import de.leonheuer.skycave.skybeecore.manager.PlayerManager
import de.leonheuer.skycave.skybeecore.util.DisplayUtil
import net.luckperms.api.LuckPerms
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class SkyBeeCore: JavaPlugin() {

    companion object {
        const val PERMISSION_GROUPS_MAX_WEIGHT = 150
        const val PREFIX = "&e&l| &eSky&6Bee &8» "
    }

    lateinit var playerManager: PlayerManager
        private set
    lateinit var econ: Economy
        private set
    lateinit var luckPerms: LuckPerms
        private set
    lateinit var worldManager: MVWorldManager
        private set
    lateinit var settings: YamlConfiguration
        private set

    @Suppress("Deprecation")
    override fun onEnable() {
        // dependencies
        val econRsp = server.servicesManager.getRegistration(Economy::class.java)
        if (econRsp == null) {
            logger.severe("Vault economy not loaded, disabling plugin.")
            server.pluginManager.disablePlugin(this)
            return
        }
        econ = econRsp.provider

        val luckPermsRsp = server.servicesManager.getRegistration(LuckPerms::class.java)
        if (luckPermsRsp == null) {
            logger.severe("LuckPerms not loaded, disabling plugin.")
            server.pluginManager.disablePlugin(this)
            return
        }
        luckPerms = luckPermsRsp.provider

        val multiverse = server.pluginManager.getPlugin("Multiverse-Core")
        if (multiverse == null || multiverse !is MultiverseCore) {
            logger.severe("Multiverse-Core not loaded, disabling plugin.")
            server.pluginManager.disablePlugin(this)
            return
        }
        worldManager = multiverse.mvWorldManager

        // configs
        if (!dataFolder.isDirectory) {
            dataFolder.mkdirs()
        }
        settings = YamlConfiguration.loadConfiguration(File(dataFolder, "settings.yml"))
        settings.options().copyDefaults(true)

        // managers
        playerManager = PlayerManager()

        // tasks
        Bukkit.getScheduler().runTaskTimer(this, Runnable{ Bukkit.getOnlinePlayers().forEach(DisplayUtil::updateScoreBoard) }, 20L, 40L)
        Bukkit.getScheduler().runTaskTimer(this, Runnable{ DisplayUtil.setTabList() }, 0L, 30L)

        // listeners
        val pm = Bukkit.getPluginManager()
        pm.registerEvents(PlayerCommand(this), this)
        pm.registerEvents(PlayerJoin(), this)
        pm.registerEvents(PlayerLeave(), this)
        pm.registerEvents(InventoryClick(), this)
        pm.registerEvents(PlayerMove(this), this)
        pm.registerEvents(PlayerTeleport(this), this)
        pm.registerEvents(PlayerPortal(), this)
        pm.registerEvents(PlayerPortal(), this)
        pm.registerEvents(PlayerToggleGlide(), this)
        pm.registerEvents(PortalCreate(), this)
        pm.registerEvents(BlockPlace(), this)
        pm.registerEvents(PlayerChangedWorld(), this)
        pm.registerEvents(PlayerChat(this), this)

        // commands
        registerCommand("wiki", WikiCommand())
        registerCommand("back", BackCommand(this))
        registerCommand("portals", PortalsCommand())
    }

    private fun registerCommand(command: String, executor: CommandExecutor) {
        val cmd = getCommand(command)
        if (cmd == null) {
            logger.severe("No entry for command $command found in the plugin.yml.")
            return
        }
        cmd.setExecutor(executor)
    }

}