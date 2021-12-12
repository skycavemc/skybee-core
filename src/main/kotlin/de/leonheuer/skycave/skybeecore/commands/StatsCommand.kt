package de.leonheuer.skycave.skybeecore.commands

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import de.leonheuer.skycave.skybeecore.enums.Message
import de.leonheuer.skycave.skybeecore.model.User
import de.leonheuer.skycave.skybeecore.util.DataUtil
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.time.format.DateTimeFormatter

class StatsCommand(private val main: SkyBeeCore): CommandExecutor {

    @Suppress("Deprecation")
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val user: User?
        val player: Player
        if (args.isEmpty()) {
            if (sender is Player) {
                user = main.dataManager.getRegisteredUser(sender)
                player = sender
            } else {
                sender.sendMessage("§c/stats <player>")
                return true
            }
        } else {
            val uuid = Bukkit.getOfflinePlayer(args[0]).uniqueId
            /*if (uuid == null) {
                sender.sendMessage(Message.UNKNOWN_PLAYER.getString().replace("%player", args[0]).get())
                return true
            }*/
            player = Bukkit.getPlayer(uuid)!!
            if (main.dataManager.isRegistered(uuid)) {
                user = main.dataManager.getRegisteredUser(Bukkit.getPlayer(uuid)!!)
            } else {
                user = DataUtil.getUser(uuid)
                if (user == null) {
                    sender.sendMessage(Message.UNKNOWN_PLAYER.getString().replace("%player", args[0]).get())
                    return true
                }
            }
        }

        val formatter = DateTimeFormatter.ofPattern("DD.MM.YY mm:hh")

        sender.sendMessage(Message.STATS_LINE.getString()
            .replace("%property", "Spielzeit:")
            .replace("%value", user.playTime.toString())
            .get(false))
        sender.sendMessage(Message.STATS_LINE.getString()
            .replace("%property", "Pollen:")
            .replace("%value", user.pollen.toString())
            .get(false))
        sender.sendMessage(Message.STATS_LINE.getString()
            .replace("%property", "Geld:")
            .replace("%value", main.econ.getBalance(player).toString())
            .get(false))
        sender.sendMessage(Message.STATS_LINE.getString()
            .replace("%property", "Kills:")
            .replace("%value", user.kills.toString())
            .get(false))
        sender.sendMessage(Message.STATS_LINE.getString()
            .replace("%property", "Tode:")
            .replace("%value", user.deaths.toString())
            .get(false))
        sender.sendMessage(Message.STATS_LINE.getString()
            .replace("%property", "K/D:")
            .replace("%value", (user.kills / user.deaths).toString())
            .get(false))

        if (sender.hasPermission("skybee.stats.team")) {
            sender.sendMessage(Message.STATS_LINE_TEAM.getString()
                .replace("%property", "UUID")
                .replace("%value", user.uuid.toString())
                .get(false))
            sender.sendMessage(Message.STATS_LINE_TEAM.getString()
                .replace("%property", "IP")
                .replace("%value", user.ipAddress.toString())
                .get(false))
            sender.sendMessage(Message.STATS_LINE_TEAM.getString()
                .replace("%property", "Zuletzt gejoint:")
                .replace("%value", formatter.format(user.lastJoined))
                .get(false))
            sender.sendMessage(Message.STATS_LINE_TEAM.getString()
                .replace("%property", "Zuletzt verlassen:")
                .replace("%value", formatter.format(user.lastLeft))
                .get(false))
            sender.sendMessage(Message.STATS_LINE_TEAM.getString()
                .replace("%property", "Spielzeit AFK:")
                .replace("%value", user.playTimeAfk.toString())
                .get(false))
        }
        return true
    }

}