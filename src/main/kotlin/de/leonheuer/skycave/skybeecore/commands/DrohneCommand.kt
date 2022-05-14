package de.leonheuer.skycave.skybeecore.commands

import de.leonheuer.skycave.skybeecore.enums.Message
import de.leonheuer.skycave.skybeecore.util.LuckPermsUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class DrohneCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Message.NO_PLAYER.getString().get(false))
            return true
        }
        val duration = LuckPermsUtil.getUserGroupExpiry(sender, "drohne")
        if (duration == null) {
            sender.sendMessage(Message.DROHNE_NOT.getString().get())
            return true
        }
        val sj = StringJoiner(", ")

        val days = duration.toDaysPart()
        if (days > 0) {
            sj.add("$days ${if (days == 1L) "Tag" else "Tage"}")
        }

        val hours = duration.toHoursPart()
        if (hours > 0) {
            sj.add("$hours ${if (hours == 1) "Stunde" else "Stunden"}")
        }

        val minutes = duration.toMinutesPart()
        if (minutes > 0) {
            sj.add("$minutes ${if (minutes == 1) "Minute" else "Minuten"}")
        }

        val seconds = duration.toSecondsPart()
        if (seconds > 0) {
            sj.add("$seconds ${if (seconds == 1) "Sekunde" else "Sekunden"}")
        }

        sender.sendMessage(Message.DROHNE_DURANCE.getString().replace("%duration", sj.toString()).get())
        return true
    }

}