package de.leonheuer.skycave.skybeecore.listener

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import de.leonheuer.skycave.skybeecore.enums.BlockedCommand
import de.leonheuer.skycave.skybeecore.enums.Message
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class CommandBlockerListener(private val main: SkyBeeCore): Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onCommand(event: PlayerCommandPreprocessEvent) {
        val player = event.player
        val cmd = event.message.split(" ")[0]
        val cmdMap = Bukkit.getCommandMap()

        /*if (!main.dataManager.getRegisteredUser(player).hasCompletedCaptcha) {
            event.isCancelled = true
            val captcha = main.playerManager.getRegisteredUser(player).captcha
            player.sendMessage(Message.CAPTCHA_NOT_DONE.getMessage().replace("%captcha", captcha!!))
            return
        }*/

        if (player.hasPermission("skybee.bypass.blockedcommands")) {
            return
        }
        if (cmdMap.getCommand(cmd.replaceFirst("/", "")) == null) {
            return
        }

        if (cmd.contains(":")) {
            val partial = cmd.split(":").toTypedArray()
            if (!partial[0].contains(" ")) {
                event.isCancelled = true
                player.sendMessage(Message.COMMAND_BLOCKED.getMessage())
            }
        } else if (cmd.lowercase().startsWith("/help")) {
            event.isCancelled = true
            player.sendMessage("")
            player.sendMessage(Message.HELP_HEADER.getFormatted())
            player.sendMessage(Message.HELP_HUB.getFormatted())
            player.sendMessage(Message.HELP_SPAWN.getFormatted())
            player.sendMessage(Message.HELP_IS.getFormatted())
            player.sendMessage(Message.HELP_MSG.getFormatted())
            player.sendMessage(Message.HELP_WIKI.getFormatted())
            player.sendMessage("")
        } else {
            for (blocked in BlockedCommand.values()) {
                if (cmd.lowercase().startsWith("/" + blocked.content)) {
                    event.isCancelled = true
                    player.sendMessage(Message.COMMAND_BLOCKED.getMessage())
                }
            }
        }
    }

}