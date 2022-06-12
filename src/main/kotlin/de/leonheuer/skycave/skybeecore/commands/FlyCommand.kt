package de.leonheuer.skycave.skybeecore.commands

import de.leonheuer.skycave.skybeecore.enums.Message
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class FlyCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Message.NO_PLAYER.getString().get(false))
            return true
        }

        if (!sender.hasPermission("player.fly") && !sender.hasPermission("essentials.fly")) {
            sender.sendMessage(Message.FLY_BUY.getString().get())
            return true
        }

        if (sender.hasPermission("player.fly") && !sender.hasPermission("essentials.fly")) {
            when (sender.location.world.name) {
                "Builder", "skybeeisland", "skyblock" -> toggleFlight(sender)
                else -> sender.sendMessage(Message.FLY_WORLD.getString().get())
            }
            return true
        }

        toggleFlight(sender)
        // TODO enable fly for others
        return true
    }

    private fun toggleFlight(player: Player) {
        if (player.allowFlight) {
            player.allowFlight = false
            player.sendMessage(Message.FLY_DEACTIVATE.getString().get())
        } else {
            player.allowFlight = true
            player.sendMessage(Message.FLY_ACTIVATE.getString().get())
        }
    }

}