package de.leonheuer.skycave.skybeecore.listener.player

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import de.leonheuer.skycave.skybeecore.enums.BlockedCommand
import de.leonheuer.skycave.skybeecore.enums.Message
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class PlayerCommand(private val main: SkyBeeCore): Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onCommand(event: PlayerCommandPreprocessEvent) {
        val player = event.player
        val cmd = event.message.split(" ")[0]

        if (cmd.lowercase().startsWith("/help")) {
            event.isCancelled = true
            player.sendMessage("")
            player.sendMessage(Message.HELP_HEADER.getString().get(false))
            player.sendMessage(Message.HELP_HUB.getString().get(false))
            player.sendMessage(Message.HELP_SPAWN.getString().get(false))
            player.sendMessage(Message.HELP_IS.getString().get(false))
            player.sendMessage(Message.HELP_MSG.getString().get(false))
            player.sendMessage(Message.HELP_WIKI.getString().get(false))
            player.sendMessage("")
            return
        }

        if (player.hasPermission("skybee.bypass.blockedcommands")) {
            return
        }
        if (main.server.commandMap.getCommand(cmd.replaceFirst("/", "")) == null) {
            return
        }

        if (cmd.contains(":")) {
            val partial = cmd.split(":").toTypedArray()
            if (!partial[0].contains(" ")) {
                event.isCancelled = true
                player.sendMessage(Message.COMMAND_BLOCKED.getString().get())
            }
        } else {
            for (blocked in BlockedCommand.values()) {
                if (cmd.lowercase().startsWith("/" + blocked.content)) {
                    event.isCancelled = true
                    player.sendMessage(Message.COMMAND_BLOCKED.getString().get())
                }
            }
        }
    }

}