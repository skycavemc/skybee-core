package de.leonheuer.skycave.skybeecore

import de.leonheuer.skycave.skybeecore.commands.WikiCommand
import de.leonheuer.skycave.skybeecore.listener.inventory.InventoryClick
import de.leonheuer.skycave.skybeecore.listener.player.PlayerCommand
import de.leonheuer.skycave.skybeecore.listener.player.PlayerJoin
import de.leonheuer.skycave.skybeecore.listener.player.PlayerLeave
import de.leonheuer.skycave.skybeecore.manager.DataManager
import de.leonheuer.skycave.skybeecore.manager.HologramManager
import de.leonheuer.skycave.skybeecore.manager.PlayerManager
import de.leonheuer.skycave.skybeecore.util.DisplayUtil
import net.luckperms.api.LuckPerms
import net.milkbowl.vault.chat.Chat
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class SkyBeeCore: JavaPlugin() {

    companion object {
        const val PERMISSION_GROUPS_MAX_WEIGHT = 150
        const val PREFIX = "&e&l| &eSky&6Bee &8» "
    }

    lateinit var hologramManager: HologramManager
        private set
    lateinit var playerManager: PlayerManager
        private set
    lateinit var dataManager: DataManager
        private set
    lateinit var econ: Economy
        private set
    lateinit var luckPerms: LuckPerms
        private set
    lateinit var chat: Chat
        private set

    @Suppress("Deprecation")
    override fun onEnable() {
        hologramManager = HologramManager(this)
        /*playerManager = PlayerManager()
        dataManager = DataManager()*/
        econ = server.servicesManager.getRegistration(Economy::class.java)!!.provider
        luckPerms = server.servicesManager.getRegistration(LuckPerms::class.java)!!.provider
        /*chat = server.servicesManager.getRegistration(Chat::class.java)!!.provider*/

        Bukkit.getScheduler().runTaskTimer(this, Runnable{ Bukkit.getOnlinePlayers().forEach(DisplayUtil::updateScoreBoard) }, 20L, 20L)
        Bukkit.getScheduler().runTaskTimer(this, Runnable{ Bukkit.getOnlinePlayers().forEach(DisplayUtil::setTabList) }, 0L, 30L)
        //Bukkit.getScheduler().runTaskTimerAsynchronously(this, Runnable{ TwitchUtil.setLiveSuffix("caveclown", "caveclown") }, 0L, 200L)

        val pm = Bukkit.getPluginManager()
        /*pm.registerEvents(ChatListener(this), this)
        pm.registerEvents(CustomDropListener(this), this)
        pm.registerEvents(PlayerDeathListener(this), this)*/
        pm.registerEvents(PlayerCommand(this), this)
        pm.registerEvents(PlayerJoin(this), this)
        pm.registerEvents(PlayerLeave(this), this)
        pm.registerEvents(InventoryClick(), this)
        /*Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord")

        getCommand("stats")!!.setExecutor(StatsCommand())*/
        getCommand("wiki")!!.setExecutor(WikiCommand())
        //getCommand("showarmor")!!.setExecutor(ShowArmorCommand(this))
    }

    /*override fun onDisable() {
        logger.info("Saving userdata...")
        dataManager.unregisterAllUsers()
    }*/

}