package de.leonheuer.skycave.skybeecore.util

import ch.njol.skript.variables.Variables
import de.leonheuer.skycave.skybeecore.SkyBeeCore
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.DisplaySlot
import java.util.*

object DisplayUtil {

    private val main = JavaPlugin.getPlugin(SkyBeeCore::class.java)
    private var changedTabList = false

    @Suppress("Deprecation")
    fun setScoreBoard(player: Player) {
        val board = Bukkit.getScoreboardManager().newScoreboard
        val obj = board.registerNewObjective("ScoreBoard", "dummy", "§f §r §f §r §e§lSky§6§lBee §r §f §r ")
        obj.displaySlot = DisplaySlot.SIDEBAR

        main.luckPerms.groupManager.loadedGroups.forEach {
            val prefix = it.cachedData.metaData.prefix!!.replace("&", "§")
            board.registerNewTeam(LuckPermsUtil.getGroupTabListName(it)).prefix = "$prefix §8| §7"
        }
        Bukkit.getOnlinePlayers().forEach {
            val group = LuckPermsUtil.getUserGroup(it)
            if (group != null) {
                board.getTeam(LuckPermsUtil.getGroupTabListName(group))!!.addPlayer(it)
            }
        }

        val money = board.registerNewTeam("money")
        money.addEntry("§1")
        money.prefix = "§r §7 §r §f${getFormattedMoney(player)}$"

        val pollen = board.registerNewTeam("pollen")
        pollen.addEntry("§3")
        pollen.prefix = "§r §7 §r §f${getPollen(player)}"

        val online = board.registerNewTeam("online")
        online.addEntry("§5")
        online.prefix = "§r §7 §r §f${Bukkit.getOnlinePlayers().size} §7/ §f50"

        obj.getScore("§0").score = 9
        obj.getScore("§7§l▸ §6Geld:").score = 8
        obj.getScore("§1").score = 7
        obj.getScore("§2").score = 6
        obj.getScore("§7§l▸ §6Pollen:").score = 5
        obj.getScore("§3").score = 4
        obj.getScore("§4").score = 3
        obj.getScore("§7§l▸ §6Online:").score = 2
        obj.getScore("§5").score = 1
        obj.getScore("§6").score = 0

        player.scoreboard = board
    }

    @Suppress("Deprecation")
    fun updateScoreBoard(player: Player) {
        val board = player.scoreboard

        val money = board.getTeam("money")
        if (money != null) {
            money.prefix = "§r §7 §r §f${getFormattedMoney(player)}$"
        }

        val pollen = board.getTeam("pollen")
        if (pollen != null) {
            pollen.prefix = "§r §7 §r §f${getPollen(player)}"
        }

        val online = board.getTeam("online")
        if (online != null) {
            online.prefix = "§r §7 §r §f${Bukkit.getOnlinePlayers().size} §7/ §f50"
        }
    }

    @Suppress("Deprecation")
    fun addPlayerToScoreboard(holder: Player, newPlayer: Player) {
        val board = holder.scoreboard
        val group = LuckPermsUtil.getUserGroup(newPlayer)
        if (group != null) {
            val team = board.getTeam(LuckPermsUtil.getGroupTabListName(group))
            team!!.addPlayer(newPlayer)
        }
    }

    @Suppress("Deprecation")
    fun removePlayerFromScoreboard(holder: Player, remove: Player) {
        val board = holder.scoreboard
        val group = LuckPermsUtil.getUserGroup(remove)
        if (group != null) {
            val team = board.getTeam(LuckPermsUtil.getGroupTabListName(group))
            team!!.removePlayer(remove)
        }

    }

    private fun getFormattedMoney(player: Player): String {
        return String.format(Locale.GERMAN, "%,.2f", main.econ.getBalance(player))
    }

    private fun getPollen(player: Player): String {
        //val user = DataUtil.getUser(player.uniqueId) ?: return "0"
        val pollen = Variables.getVariable("pollen.${player.uniqueId}", null, false) ?: return "0"
        return pollen.toString()
    }

    fun setTabList(player: Player) {
        if (changedTabList) {
            player.sendPlayerListHeaderAndFooter(
                Component.text("§f§lSky§3§lCave§b§l.de\n §8» §fDein §bSkyBlock §eNetzwerk! §8« \n"),
                Component.text("\n§7✦ §eJoine unserem /discord!§r §7✦")
            )
        } else {
            player.sendPlayerListHeaderAndFooter(
                Component.text("§f§lSky§3§lCave§b§l.de\n §8» §7Dein §9SkyBlock §6Netzwerk! §8« \n"),
                Component.text("\n§7✦ §6Joine unserem /discord!§r §7✦")
            )
        }
        changedTabList = !changedTabList
    }

}