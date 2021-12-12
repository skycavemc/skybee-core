package de.leonheuer.skycave.skybeecore.manager

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import de.leonheuer.skycave.skybeecore.util.LuckPermsUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class TabListManager(private val main: SkyBeeCore) {

    private val sbm = Bukkit.getScoreboardManager()!!

    fun setScoreboard(player: Player) {
        player.scoreboard = sbm.newScoreboard
    }

    fun updatePlayerName(player: Player) {

    }

}