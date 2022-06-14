package de.leonheuer.skycave.skybeecore.util

import com.mongodb.client.model.Filters
import de.leonheuer.skycave.skybeecore.SkyBeeCore
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
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
        val obj = board.registerNewObjective("ScoreBoard", "dummy", "§f §r §f §r §f§lSky§3§lCave§b§l.de §r §f §r ")
        obj.displaySlot = DisplaySlot.SIDEBAR

        main.luckPerms.groupManager.loadedGroups.forEach {
            var prefix = it.cachedData.metaData.prefix ?: return@forEach
            prefix = prefix.replace("&", "§")
            board.registerNewTeam(LuckPermsUtil.getGroupTabListName(it)).prefix = "$prefix §8| §7"
        }
        Bukkit.getOnlinePlayers().forEach {
            val group = LuckPermsUtil.getUserGroup(it)
            if (group != null) {
                val team = board.getTeam(LuckPermsUtil.getGroupTabListName(group)) ?: return@forEach
                team.addPlayer(it)
            }
        }

        val money = board.registerNewTeam("money")
        money.addEntry("§1")
        money.prefix = "§r §7 §r §f${getFormattedMoney(player)}$"

        val vc = board.registerNewTeam("vc")
        vc.addEntry("§3")
        vc.prefix = "§r §7 §r §f${getVoteCoins(player)}"

        val online = board.registerNewTeam("online")
        online.addEntry("§5")
        online.prefix = "§r §7 §r §f${Bukkit.getOnlinePlayers().size} §7/ §f" + SkyBeeCore.MAX_PLAYERS

        obj.getScore("§0").score = 9
        obj.getScore("§7§l▸ §3Geld:").score = 8
        obj.getScore("§1").score = 7
        obj.getScore("§2").score = 6
        obj.getScore("§7§l▸ §3VoteCoins:").score = 5
        obj.getScore("§3").score = 4
        obj.getScore("§4").score = 3
        obj.getScore("§7§l▸ §3Online:").score = 2
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

        val vc = board.getTeam("vc")
        if (vc != null) {
            vc.prefix = "§r §7 §r §f${getVoteCoins(player)}"
        }

        val online = board.getTeam("online")
        if (online != null) {
            online.prefix = "§r §7 §r §f${Bukkit.getOnlinePlayers().size} §7/ §f" + SkyBeeCore.MAX_PLAYERS
        }
    }

    private fun getVoteCoins(player: Player): Long {
        val user = main.users.find(Filters.eq("uuid", player.uniqueId.toString())).first()
        return user?.voteCoins ?: 0
    }

    @Suppress("Deprecation")
    fun addPlayerToScoreboard(holder: Player, newPlayer: Player) {
        val board = holder.scoreboard
        val group = LuckPermsUtil.getUserGroup(newPlayer)
        if (group != null) {
            val team = board.getTeam(LuckPermsUtil.getGroupTabListName(group)) ?: return
            team.addPlayer(newPlayer)
        }
    }

    @Suppress("Deprecation")
    fun removePlayerFromScoreboard(holder: Player, remove: Player) {
        val board = holder.scoreboard
        val group = LuckPermsUtil.getUserGroup(remove)
        if (group != null) {
            val team = board.getTeam(LuckPermsUtil.getGroupTabListName(group)) ?: return
            team.removePlayer(remove)
        }

    }

    private fun getFormattedMoney(player: Player): String {
        return String.format(Locale.GERMAN, "%,.2f", main.econ.getBalance(player))
    }

    fun setTabList() {
        val header: TextComponent
        val footer: TextComponent
        if (changedTabList) {
            header = Component.text("§f§lSky§3§lCave§b§l.de\n §8» §fDein §bSkyBlock §eNetzwerk! §8« \n")
            footer = Component.text("\n§7✦ §eJoine unserem /discord!§r §7✦")
        } else {
            header = Component.text("§f§lSky§3§lCave§b§l.de\n §8» §7Dein §9SkyBlock §6Netzwerk! §8« \n")
            footer = Component.text("\n§7✦ §6Joine unserem /discord!§r §7✦")
        }
        for (player in main.server.onlinePlayers) {
            player.sendPlayerListHeaderAndFooter(header, footer)
        }
        changedTabList = !changedTabList
    }

}