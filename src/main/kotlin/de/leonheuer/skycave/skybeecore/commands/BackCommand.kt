package de.leonheuer.skycave.skybeecore.commands

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import de.leonheuer.skycave.skybeecore.enums.Message
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class BackCommand(private val main: SkyBeeCore): CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val from = main.playerManager.fromLocation[sender]
            if (from == null) {
                sender.sendMessage(Message.COMMAND_BACK_ERROR.getString().get())
                return true
            }
            sender.sendMessage(Message.COMMAND_BACK_SUCCESS.getString().get())
            sender.teleport(from)
        }
        return true
    }

}