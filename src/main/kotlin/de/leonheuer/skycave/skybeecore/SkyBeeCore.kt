package de.leonheuer.skycave.skybeecore

import de.leonheuer.skycave.skybeecore.commands.BackCommand
import de.leonheuer.skycave.skybeecore.commands.WikiCommand
import de.leonheuer.skycave.skybeecore.listener.inventory.InventoryClick
import de.leonheuer.skycave.skybeecore.listener.player.*
import de.leonheuer.skycave.skybeecore.manager.PlayerManager
import de.leonheuer.skycave.skybeecore.util.DisplayUtil
import net.luckperms.api.LuckPerms
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.plugin.java.JavaPlugin

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

    @Suppress("Deprecation")
    override fun onEnable() {
        playerManager = PlayerManager()
        econ = server.servicesManager.getRegistration(Economy::class.java)!!.provider
        luckPerms = server.servicesManager.getRegistration(LuckPerms::class.java)!!.provider

        Bukkit.getScheduler().runTaskTimer(this, Runnable{ Bukkit.getOnlinePlayers().forEach(DisplayUtil::updateScoreBoard) }, 20L, 40L)
        Bukkit.getScheduler().runTaskTimer(this, Runnable{ DisplayUtil.setTabList() }, 0L, 30L)

        val pm = Bukkit.getPluginManager()
        pm.registerEvents(PlayerCommand(this), this)
        pm.registerEvents(PlayerJoin(), this)
        pm.registerEvents(PlayerLeave(), this)
        pm.registerEvents(InventoryClick(), this)
        pm.registerEvents(PlayerMove(this), this)
        pm.registerEvents(PlayerTeleport(this), this)

        registerCommand("wiki", WikiCommand())
        registerCommand("back", BackCommand(this))
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