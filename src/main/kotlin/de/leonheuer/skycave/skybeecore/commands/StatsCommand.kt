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
            val uuid = Bukkit.getPlayerUniqueId(args[0])
            if (uuid == null) {
                sender.sendMessage(Message.UNKNOWN_PLAYER.getMessage().replace("%player", args[0]))
                return true
            }
            player = Bukkit.getPlayer(uuid)!!
            if (main.dataManager.isRegistered(uuid)) {
                user = main.dataManager.getRegisteredUser(Bukkit.getPlayer(uuid)!!)
            } else {
                user = DataUtil.getUser(uuid)
                if (user == null) {
                    sender.sendMessage(Message.UNKNOWN_PLAYER.getMessage().replace("%player", args[0]))
                    return true
                }
            }
        }

        val formatter = DateTimeFormatter.ofPattern("DD.MM.YY mm:hh")

        sender.sendMessage(Message.STATS_LINE.getFormatted()
            .replace("%property", "Spielzeit:")
            .replace("%value", user.playTime.toString()))
        sender.sendMessage(Message.STATS_LINE.getFormatted()
            .replace("%property", "Pollen:")
            .replace("%value", user.pollen.toString()))
        sender.sendMessage(Message.STATS_LINE.getFormatted()
            .replace("%property", "Geld:")
            .replace("%value", main.econ.getBalance(player).toString()))
        sender.sendMessage(Message.STATS_LINE.getFormatted()
            .replace("%property", "Kills:")
            .replace("%value", user.kills.toString()))
        sender.sendMessage(Message.STATS_LINE.getFormatted()
            .replace("%property", "Tode:")
            .replace("%value", user.deaths.toString()))
        sender.sendMessage(Message.STATS_LINE.getFormatted()
            .replace("%property", "K/D:")
            .replace("%value", (user.kills / user.deaths).toString()))

        if (sender.hasPermission("skybee.stats.team")) {
            sender.sendMessage(Message.STATS_LINE_TEAM.getFormatted()
                .replace("%property", "UUID")
                .replace("%value", user.uuid.toString()))
            sender.sendMessage(Message.STATS_LINE_TEAM.getFormatted()
                .replace("%property", "IP")
                .replace("%value", user.ipAddress.toString()))
            sender.sendMessage(Message.STATS_LINE_TEAM.getFormatted()
                .replace("%property", "Zuletzt gejoint:")
                .replace("%value", formatter.format(user.lastJoined)))
            sender.sendMessage(Message.STATS_LINE_TEAM.getFormatted()
                .replace("%property", "Zuletzt verlassen:")
                .replace("%value", formatter.format(user.lastLeft)))
            sender.sendMessage(Message.STATS_LINE_TEAM.getFormatted()
                .replace("%property", "Spielzeit AFK:")
                .replace("%value", user.playTimeAfk.toString()))
        }
        return true
    }

}